package com.ottr.lab.domain.subscription

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 구독 결제 주기를 나타내는 Enum
 *
 * @property value DB 저장 값 (소문자)
 */
enum class BillingCycle(val value: String) {
    @JsonProperty("monthly")
    MONTHLY("monthly"),

    @JsonProperty("yearly")
    YEARLY("yearly"),
}
