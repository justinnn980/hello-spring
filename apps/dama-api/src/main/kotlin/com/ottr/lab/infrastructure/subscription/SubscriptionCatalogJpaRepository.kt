package com.ottr.lab.infrastructure.subscription

import com.ottr.lab.domain.subscription.SubscriptionModel
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Spring Data JPA를 활용한 구독 서비스 Repository
 */
interface SubscriptionCatalogJpaRepository : JpaRepository<SubscriptionModel, Long> {
    fun findByServiceName(serviceName: String): SubscriptionModel?
}
