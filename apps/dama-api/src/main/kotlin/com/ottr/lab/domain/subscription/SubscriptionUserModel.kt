package com.ottr.lab.domain.subscription

import com.ottr.lab.domain.BaseEntity
import com.ottr.lab.support.error.CoreException
import com.ottr.lab.support.error.ErrorType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate

/**
 * 사용자별 구독 정보를 나타내는 엔티티
 *
 * @property userId 사용자 ID
 * @property subscription 구독 서비스 정보
 * @property billingCycle 결제 주기 (MONTHLY, YEARLY)
 * @property price 구독 가격
 * @property paymentDay 결제일 (1~31)
 * @property startedAt 구독 시작일
 * @property endedAt 구독 종료일
 * @property memo 메모
 */
@Entity
@Table(name = "subscriptions_users")
class SubscriptionUserModel(
    userId: Long,
    subscription: SubscriptionModel,
    billingCycle: BillingCycle? = null,
    price: Int? = null,
    paymentDay: Int? = null,
    startedAt: LocalDate? = null,
    endedAt: LocalDate? = null,
    memo: String? = null,
) : BaseEntity() {
    @Column(name = "user_id", nullable = false)
    var userId: Long = userId
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", nullable = false)
    var subscription: SubscriptionModel = subscription
        protected set

    @Column(name = "billing_cycle", length = 20)
    var billingCycle: BillingCycle? = billingCycle
        protected set

    @Column(name = "price")
    var price: Int? = price
        protected set

    @Column(name = "payment_day")
    var paymentDay: Int? = paymentDay
        protected set

    @Column(name = "started_at")
    var startedAt: LocalDate? = startedAt
        protected set

    @Column(name = "ended_at")
    var endedAt: LocalDate? = endedAt
        protected set

    @Column(name = "memo", columnDefinition = "TEXT")
    var memo: String? = memo
        protected set

    init {
        validatePrice(price)
        validatePaymentDay(paymentDay)
    }

    override fun guard() {
        validatePrice(price)
        validatePaymentDay(paymentDay)
    }

    private fun validatePrice(price: Int?) {
        if (price != null && price < 0) {
            throw CoreException(ErrorType.BAD_REQUEST, "가격은 0 이상이어야 합니다.")
        }
    }

    private fun validatePaymentDay(paymentDay: Int?) {
        if (paymentDay != null && (paymentDay < 1 || paymentDay > 31)) {
            throw CoreException(ErrorType.BAD_REQUEST, "결제일은 1~31 사이여야 합니다.")
        }
    }
}
