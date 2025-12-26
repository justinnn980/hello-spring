package com.ottr.lab.domain.subscription

import com.ottr.lab.domain.BaseEntity
import com.ottr.lab.support.error.CoreException
import com.ottr.lab.support.error.ErrorType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

/**
 * 사용자의 구독 정보를 나타내는 엔티티
 *
 * @property serviceName 구독 서비스명
 */
@Entity
@Table(name = "subscriptions")
class SubscriptionModel(
    serviceName: String,
) : BaseEntity() {
    @Column(name = "name", nullable = false, length = 255)
    var serviceName: String = serviceName
        protected set

    init {
        validateServiceName(serviceName)
    }

    /**
     * 엔티티 검증 (BaseEntity의 guard 메서드 오버라이드)
     */
    override fun guard() {
        validateServiceName(serviceName)
    }

    private fun validateServiceName(serviceName: String) {
        if (serviceName.isBlank()) {
            throw CoreException(ErrorType.BAD_REQUEST, "서비스명은 필수입니다.")
        }
        if (serviceName.length > 255) {
            throw CoreException(ErrorType.BAD_REQUEST, "서비스명은 255자를 초과할 수 없습니다.")
        }
    }
}
