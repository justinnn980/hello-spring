package com.ottr.lab.interfaces.api.subscription

import com.fasterxml.jackson.annotation.JsonProperty
import com.ottr.lab.domain.subscription.BillingCycle
import com.ottr.lab.domain.subscription.SubscriptionUserModel
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.ZonedDateTime

class SubscriptionV1Dto {
    /**
     * 비용 비교 타입 (절약 중인지/더 쓰고 있는지)
     */
    enum class CostComparisonType {
        @JsonProperty("down")
        DOWN, // 절약 중

        @JsonProperty("up")
        UP, // 더 쓰는 중

        @JsonProperty("same")
        SAME, // 동일
    }

    /**
     * 구독 생성 요청 DTO
     */
    data class CreateSubscriptionRequest(
        @field:NotBlank(message = "서비스명은 필수입니다.")
        @field:Size(min = 1, max = 255, message = "서비스명은 1~255자여야 합니다.")
        @JsonProperty("service_name")
        val serviceName: String,

        @JsonProperty("billing_cycle")
        val billingCycle: BillingCycle? = null,

        @field:Min(value = 0, message = "가격은 0 이상이어야 합니다.")
        @JsonProperty("price")
        val price: Int? = null,

        @field:Min(value = 1, message = "결제일은 1~31 사이여야 합니다.")
        @field:Max(value = 31, message = "결제일은 1~31 사이여야 합니다.")
        @JsonProperty("payment_day")
        val paymentDay: Int? = null,

        @JsonProperty("memo")
        val memo: String? = null,

        @JsonProperty("started_at")
        val startedAt: LocalDate? = null,
    )

    /**
     * 구독 요약 정보 응답 DTO (리스트 조회용)
     */
    data class SubscriptionSummaryResponse(
        @JsonProperty("id")
        val id: Long,

        @JsonProperty("service_name")
        val serviceName: String,

        @JsonProperty("billing_cycle")
        val billingCycle: BillingCycle?,

        @JsonProperty("price")
        val price: Int?,

        @JsonProperty("payment_day")
        val paymentDay: Int?,
    ) {
        companion object {
            /**
             * SubscriptionUserModel을 SubscriptionSummaryResponse로 변환합니다.
             *
             * @param subscriptionUser 사용자 구독 모델
             * @return 구독 요약 응답 DTO
             */
            fun from(subscriptionUser: SubscriptionUserModel): SubscriptionSummaryResponse {
                return SubscriptionSummaryResponse(
                    id = subscriptionUser.id,
                    serviceName = subscriptionUser.subscription.serviceName,
                    billingCycle = subscriptionUser.billingCycle,
                    price = subscriptionUser.price,
                    paymentDay = subscriptionUser.paymentDay,
                )
            }
        }
    }

    /**
     * 구독 상세 정보 응답 DTO
     * 보안을 위해 user_id는 응답에 포함하지 않습니다.
     */
    data class SubscriptionDetailResponse(
        @JsonProperty("id")
        val id: Long,

        @JsonProperty("service_name")
        val serviceName: String,

        @JsonProperty("billing_cycle")
        val billingCycle: BillingCycle?,

        @JsonProperty("price")
        val price: Int?,

        @JsonProperty("payment_day")
        val paymentDay: Int?,

        @JsonProperty("memo")
        val memo: String?,

        @JsonProperty("created_at")
        val createdAt: ZonedDateTime,

        @JsonProperty("updated_at")
        val updatedAt: ZonedDateTime,
    ) {
        companion object {
            /**
             * SubscriptionUserModel을 SubscriptionDetailResponse로 변환합니다.
             *
             * @param subscriptionUser 사용자 구독 모델
             * @return 구독 상세 응답 DTO
             */
            fun from(subscriptionUser: SubscriptionUserModel): SubscriptionDetailResponse {
                return SubscriptionDetailResponse(
                    id = subscriptionUser.id,
                    serviceName = subscriptionUser.subscription.serviceName,
                    billingCycle = subscriptionUser.billingCycle,
                    price = subscriptionUser.price,
                    paymentDay = subscriptionUser.paymentDay,
                    memo = subscriptionUser.memo,
                    createdAt = subscriptionUser.createdAt,
                    updatedAt = subscriptionUser.updatedAt,
                )
            }
        }
    }

    /**
     * 월 구독 비용 응답 DTO
     */
    data class MonthlySubscriptionCostResponse(
        @JsonProperty("total_monthly_cost")
        val totalMonthlyCost: Int,

        @JsonProperty("comparison_amount")
        val comparisonAmount: Int,

        @JsonProperty("comparison_type")
        val comparisonType: CostComparisonType,

        @JsonProperty("comparison_message")
        val comparisonMessage: String,
    )

    /**
     * 내 구독 리스트 상세 응답 DTO
     */
    data class MySubscriptionDetailsResponse(
        @JsonProperty("user_name")
        val userName: String,

        @JsonProperty("subscriptions")
        val subscriptions: List<SubscriptionDetailInfo>,
    )

    /**
     * 구독 상세 정보 (내 구독 리스트용)
     */
    data class SubscriptionDetailInfo(
        @JsonProperty("service_name")
        val serviceName: String,

        @JsonProperty("price")
        val price: Int?,

        @JsonProperty("payment_day")
        val paymentDay: Int?,

        @JsonProperty("months_subscribed")
        val monthsSubscribed: Int,

        @JsonProperty("payment_notice")
        val paymentNotice: String?,
    )

    /**
     * 구독 랭킹 응답 DTO
     */
    data class SubscriptionRankingResponse(
        @JsonProperty("service_name")
        val serviceName: String,

        @JsonProperty("subscriber_count")
        val subscriberCount: Long,

        @JsonProperty("age")
        val age: Int?,
    )
}
