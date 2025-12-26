package com.ottr.lab.domain.subscription

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

/**
 * BillingCycle Enum을 DB에 저장할 때 소문자로 변환하는 Converter
 */
@Converter(autoApply = true)
class BillingCycleConverter : AttributeConverter<BillingCycle, String> {
    override fun convertToDatabaseColumn(attribute: BillingCycle?): String? {
        return attribute?.value
    }

    override fun convertToEntityAttribute(dbData: String?): BillingCycle? {
        return dbData?.let { data ->
            BillingCycle.entries.find { it.value == data }
        }
    }
}
