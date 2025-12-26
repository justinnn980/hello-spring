package com.ottr.lab.domain.subscription

import com.ottr.lab.domain.user.UserRepository
import com.ottr.lab.support.error.CoreException
import com.ottr.lab.support.error.ErrorType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.Period
import java.time.YearMonth
import java.time.ZonedDateTime

/**
 * 구독 관리 서비스
 *
 * @property subscriptionRepository 구독 정보 Repository
 * @property userRepository 사용자 Repository
 */
@Service
class SubscriptionService(
    private val subscriptionRepository: SubscriptionRepository,
    private val userRepository: UserRepository,
) {
    /**
     * 새로운 구독을 생성합니다.
     *
     * @param userId 사용자 ID
     * @param serviceName 구독 서비스명
     * @param billingCycle 결제 주기
     * @param price 구독 가격
     * @param paymentDay 결제일
     * @param memo 메모
     * @param startedAt 구독 시작일
     * @return 생성된 구독 정보
     */
    @Transactional
    fun createSubscription(
        userId: Long,
        serviceName: String,
        billingCycle: BillingCycle?,
        price: Int?,
        paymentDay: Int?,
        memo: String?,
        startedAt: LocalDate?,
    ): SubscriptionUserModel {
        val subscription = subscriptionRepository.findSubscriptionByServiceName(serviceName)
            ?: subscriptionRepository.saveSubscription(SubscriptionModel(serviceName))

        val subscriptionUser = SubscriptionUserModel(
            userId = userId,
            subscription = subscription,
            billingCycle = billingCycle,
            price = price,
            paymentDay = paymentDay,
            memo = memo,
            startedAt = startedAt,
        )

        return subscriptionRepository.save(subscriptionUser)
    }

    /**
     * 사용자의 구독 목록을 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 구독 목록 (최신 생성 순)
     */
    @Transactional(readOnly = true)
    fun getSubscriptionsByUserId(userId: Long): List<SubscriptionUserModel> {
        return subscriptionRepository.findByUserId(userId)
    }

    /**
     * 구독을 삭제합니다. (soft delete)
     * 다른 사용자의 구독 정보에 접근하려고 하면 404 에러를 발생시킵니다.
     *
     * @param id 구독 ID
     * @param userId 사용자 ID
     * @throws CoreException 구독이 존재하지 않거나 다른 사용자의 구독인 경우
     */
    @Transactional
    fun deleteSubscription(id: Long, userId: Long) {
        val subscription = subscriptionRepository.findById(id)
            ?: throw CoreException(ErrorType.NOT_FOUND, "구독 정보를 찾을 수 없습니다.")

        // 다른 사용자의 구독에 접근하려는 경우 404 반환 (보안상 존재 여부를 노출하지 않기 위함)
        if (subscription.userId != userId) {
            throw CoreException(ErrorType.NOT_FOUND, "구독 정보를 찾을 수 없습니다.")
        }

        subscription.delete()
        subscriptionRepository.save(subscription)
    }

    /**
     * 월 구독 비용 정보를 조회합니다.
     * 이번 달 총 비용과 지난달 대비 비교 정보를 반환합니다.
     *
     * @param userId 사용자 ID
     * @return 월 구독 비용, 지난달 대비 비교 금액, 비교 타입, 비교 메시지
     */
    @Transactional(readOnly = true)
    fun getMonthlySubscriptionCost(userId: Long): MonthlySubscriptionCostData {
        val now = ZonedDateTime.now()
        val currentMonthSubscriptions = subscriptionRepository.findByUserId(userId)
        val currentMonthlyCost = calculateMonthlyCost(currentMonthSubscriptions)

        // 지난달 말일 기준으로 활성화되어 있던 구독들 조회
        val lastMonthEnd = YearMonth.from(now.minusMonths(1)).atEndOfMonth().atTime(23, 59, 59)
        val lastMonthZonedDateTime = ZonedDateTime.of(lastMonthEnd, now.zone)
        val lastMonthSubscriptions =
            subscriptionRepository.findActiveSubscriptionsAtTime(userId, lastMonthZonedDateTime)
        val lastMonthlyCost = calculateMonthlyCost(lastMonthSubscriptions)

        val comparisonAmount = kotlin.math.abs(currentMonthlyCost - lastMonthlyCost)
        val comparisonType = when {
            currentMonthlyCost < lastMonthlyCost -> CostComparisonType.DOWN
            currentMonthlyCost > lastMonthlyCost -> CostComparisonType.UP
            else -> CostComparisonType.SAME
        }

        val comparisonMessage = when (comparisonType) {
            CostComparisonType.DOWN -> "지난달보다 ${comparisonAmount}원 절약 중!"
            CostComparisonType.UP -> "지난달보다 ${comparisonAmount}원 더 지출 중"
            CostComparisonType.SAME -> "지난달과 동일한 비용입니다"
        }

        return MonthlySubscriptionCostData(
            totalMonthlyCost = currentMonthlyCost,
            comparisonAmount = comparisonAmount,
            comparisonType = comparisonType,
            comparisonMessage = comparisonMessage,
        )
    }

    /**
     * 내 구독 리스트 상세 정보를 조회합니다.
     * 최대 5개의 구독만 반환합니다.
     *
     * @param userId 사용자 ID
     * @return 사용자 이름과 구독 리스트 상세 정보
     */
    @Transactional(readOnly = true)
    fun getMySubscriptionDetails(userId: Long): MySubscriptionDetailsData {
        val user = userRepository.findById(userId)
            ?: throw CoreException(ErrorType.NOT_FOUND, "사용자를 찾을 수 없습니다.")

        val subscriptions = subscriptionRepository.findByUserId(userId)
            .take(5)

        val today = LocalDate.now()
        val subscriptionDetails = subscriptions.map { subscription ->
            val monthsSubscribed = Period.between(
                subscription.createdAt.toLocalDate(),
                today,
            ).toTotalMonths().toInt()

            val paymentNotice = subscription.paymentDay?.let { paymentDay ->
                val daysUntilPayment = calculateDaysUntilPayment(today, paymentDay)
                if (daysUntilPayment in 1..3) {
                    "${daysUntilPayment}일 뒤 결제"
                } else {
                    null
                }
            }

            SubscriptionDetailData(
                serviceName = subscription.subscription.serviceName,
                price = subscription.price,
                paymentDay = subscription.paymentDay,
                monthsSubscribed = monthsSubscribed,
                paymentNotice = paymentNotice,
            )
        }

        return MySubscriptionDetailsData(
            userName = user.nickname ?: user.email,
            subscriptions = subscriptionDetails,
        )
    }

    /**
     * 구독 랭킹을 조회합니다.
     *
     * @param age 연령대 필터 (선택, null이면 전체)
     * @return 서비스별 구독자 수 랭킹
     */
    @Transactional(readOnly = true)
    fun getSubscriptionRanking(age: Int?): List<SubscriptionRankingData> {
        val rankings = if (age != null) {
            subscriptionRepository.countSubscribersByServiceAndAge(age)
        } else {
            subscriptionRepository.countSubscribersByService()
        }

        return rankings.map { (serviceName, count) ->
            SubscriptionRankingData(
                serviceName = serviceName,
                subscriberCount = count,
            )
        }
    }

    /**
     * 월 구독 비용을 계산합니다.
     * billing_cycle이 monthly인 구독의 price 합계를 반환합니다.
     *
     * @param subscriptions 구독 목록
     * @return 월 총 비용
     */
    private fun calculateMonthlyCost(subscriptions: List<SubscriptionUserModel>): Int {
        return subscriptions
            .filter { it.billingCycle == BillingCycle.MONTHLY }
            .sumOf { it.price ?: 0 }
    }

    /**
     * 오늘부터 다음 결제일까지의 일수를 계산합니다.
     *
     * @param today 오늘 날짜
     * @param paymentDay 결제일 (1~31)
     * @return 다음 결제일까지의 일수
     */
    private fun calculateDaysUntilPayment(today: LocalDate, paymentDay: Int): Int {
        val currentMonth = YearMonth.from(today)
        val paymentDate = try {
            currentMonth.atDay(paymentDay.coerceIn(1, currentMonth.lengthOfMonth()))
        } catch (e: Exception) {
            currentMonth.atDay(currentMonth.lengthOfMonth())
        }

        return if (paymentDate.isAfter(today)) {
            Period.between(today, paymentDate).days
        } else {
            val nextMonthPaymentDate = currentMonth.plusMonths(1)
                .atDay(paymentDay.coerceIn(1, currentMonth.plusMonths(1).lengthOfMonth()))
            Period.between(today, nextMonthPaymentDate).days
        }
    }

    /**
     * 비용 비교 타입
     */
    enum class CostComparisonType {
        DOWN, // 절약 중
        UP, // 더 쓰는 중
        SAME, // 동일
    }

    /**
     * 월 구독 비용 데이터
     */
    data class MonthlySubscriptionCostData(
        val totalMonthlyCost: Int,
        val comparisonAmount: Int,
        val comparisonType: CostComparisonType,
        val comparisonMessage: String,
    )

    /**
     * 내 구독 리스트 상세 데이터
     */
    data class MySubscriptionDetailsData(
        val userName: String,
        val subscriptions: List<SubscriptionDetailData>,
    )

    /**
     * 구독 상세 데이터
     */
    data class SubscriptionDetailData(
        val serviceName: String,
        val price: Int?,
        val paymentDay: Int?,
        val monthsSubscribed: Int,
        val paymentNotice: String?,
    )

    /**
     * 구독 랭킹 데이터
     */
    data class SubscriptionRankingData(
        val serviceName: String,
        val subscriberCount: Long,
    )
}
