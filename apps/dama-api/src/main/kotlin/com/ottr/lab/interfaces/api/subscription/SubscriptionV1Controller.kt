package com.ottr.lab.interfaces.api.subscription

import com.ottr.lab.domain.subscription.SubscriptionService
import com.ottr.lab.interfaces.api.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * 구독 관리 API Controller
 *
 * @property subscriptionService 구독 서비스
 */
@RestController
@RequestMapping("/api/subscriptions")
class SubscriptionV1Controller(
    private val subscriptionService: SubscriptionService,
) : SubscriptionV1ApiSpec {
    /**
     * 새로운 구독을 등록합니다.
     *
     * POST /api/subscriptions/{user_id}
     *
     * @param userId 사용자 ID (path로 전달)
     * @param request 구독 생성 요청
     * @return 생성된 구독 정보
     */
    @PostMapping("/{user_id}")
    @ResponseStatus(HttpStatus.CREATED)
    override fun createSubscription(
        @PathVariable("user_id") userId: Long,
        @Valid @RequestBody request: SubscriptionV1Dto.CreateSubscriptionRequest,
    ): ApiResponse<SubscriptionV1Dto.SubscriptionSummaryResponse> {
        return subscriptionService.createSubscription(
            userId = userId,
            serviceName = request.serviceName,
            billingCycle = request.billingCycle,
            price = request.price,
            paymentDay = request.paymentDay,
            memo = request.memo,
            startedAt = request.startedAt,
        )
            .let { SubscriptionV1Dto.SubscriptionSummaryResponse.from(it) }
            .let { ApiResponse.success(it) }
    }

    /**
     * 사용자의 구독 목록을 조회합니다.
     *
     * GET /api/subscriptions/{user_id}
     *
     * @param userId 사용자 ID (path로 전달)
     * @return 구독 목록
     */
    @GetMapping("/{user_id}")
    override fun getSubscriptions(
        @PathVariable("user_id") userId: Long,
    ): ApiResponse<List<SubscriptionV1Dto.SubscriptionSummaryResponse>> {
        return subscriptionService.getSubscriptionsByUserId(userId)
            .map { SubscriptionV1Dto.SubscriptionSummaryResponse.from(it) }
            .let { ApiResponse.success(it) }
    }

    /**
     * 월 구독 비용을 조회합니다.
     *
     * GET /api/subscriptions/{user_id}/monthly-cost
     *
     * @param userId 사용자 ID (path로 전달)
     * @return 월 구독 비용 정보
     */
    @GetMapping("/{user_id}/monthly-cost")
    override fun getMonthlySubscriptionCost(
        @PathVariable("user_id") userId: Long,
    ): ApiResponse<SubscriptionV1Dto.MonthlySubscriptionCostResponse> {
        val result = subscriptionService.getMonthlySubscriptionCost(userId)

        val comparisonType = when (result.comparisonType) {
            SubscriptionService.CostComparisonType.DOWN -> SubscriptionV1Dto.CostComparisonType.DOWN
            SubscriptionService.CostComparisonType.UP -> SubscriptionV1Dto.CostComparisonType.UP
            SubscriptionService.CostComparisonType.SAME -> SubscriptionV1Dto.CostComparisonType.SAME
        }

        return SubscriptionV1Dto.MonthlySubscriptionCostResponse(
            totalMonthlyCost = result.totalMonthlyCost,
            comparisonAmount = result.comparisonAmount,
            comparisonType = comparisonType,
            comparisonMessage = result.comparisonMessage,
        ).let { ApiResponse.success(it) }
    }

    /**
     * 내 구독 리스트 상세 정보를 조회합니다.
     *
     * GET /api/subscriptions/{user_id}/my-details
     *
     * @param userId 사용자 ID (path로 전달)
     * @return 사용자 이름과 구독 리스트 상세 정보
     */
    @GetMapping("/{user_id}/my-details")
    override fun getMySubscriptionDetails(
        @PathVariable("user_id") userId: Long,
    ): ApiResponse<SubscriptionV1Dto.MySubscriptionDetailsResponse> {
        val result = subscriptionService.getMySubscriptionDetails(userId)

        val subscriptions = result.subscriptions.map {
            SubscriptionV1Dto.SubscriptionDetailInfo(
                serviceName = it.serviceName,
                price = it.price,
                paymentDay = it.paymentDay,
                monthsSubscribed = it.monthsSubscribed,
                paymentNotice = it.paymentNotice,
            )
        }

        return SubscriptionV1Dto.MySubscriptionDetailsResponse(
            userName = result.userName,
            subscriptions = subscriptions,
        ).let { ApiResponse.success(it) }
    }

    /**
     * 구독 랭킹을 조회합니다.
     *
     * GET /api/subscriptions/ranking?age={age}
     *
     * @param age 연령대 필터 (선택)
     * @return 서비스별 구독자 수 랭킹
     */
    @GetMapping("/ranking")
    override fun getSubscriptionRanking(
        @RequestParam(name = "age", required = false) age: Int?,
    ): ApiResponse<List<SubscriptionV1Dto.SubscriptionRankingResponse>> {
        return subscriptionService.getSubscriptionRanking(age)
            .map {
                SubscriptionV1Dto.SubscriptionRankingResponse(
                    serviceName = it.serviceName,
                    subscriberCount = it.subscriberCount,
                )
            }
            .let { ApiResponse.success(it) }
    }

    /**
     * 구독을 삭제합니다. (soft delete)
     *
     * PUT /api/subscriptions/{user_id}/{id}/delete
     *
     * @param id 구독 ID
     * @param userId 사용자 ID (path로 전달)
     * @return 성공 응답
     */
    @PutMapping("/{user_id}/{id}/delete")
    override fun deleteSubscription(
        @PathVariable id: Long,
        @PathVariable("user_id") userId: Long,
    ): ApiResponse<Unit> {
        subscriptionService.deleteSubscription(id, userId)
        return ApiResponse.success(Unit)
    }
}
