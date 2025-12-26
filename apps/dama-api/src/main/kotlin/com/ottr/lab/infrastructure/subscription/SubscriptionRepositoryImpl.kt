package com.ottr.lab.infrastructure.subscription

import com.ottr.lab.domain.subscription.SubscriptionModel
import com.ottr.lab.domain.subscription.SubscriptionRepository
import com.ottr.lab.domain.subscription.SubscriptionUserModel
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime

/**
 * SubscriptionRepository의 구현체
 *
 * @property subscriptionUserJpaRepository 사용자별 구독 JPA Repository
 * @property subscriptionCatalogJpaRepository 구독 서비스 JPA Repository
 */
@Repository
class SubscriptionRepositoryImpl(
    private val subscriptionUserJpaRepository: SubscriptionUserJpaRepository,
    private val subscriptionCatalogJpaRepository: SubscriptionCatalogJpaRepository,
) : SubscriptionRepository {
    override fun save(subscriptionUser: SubscriptionUserModel): SubscriptionUserModel {
        return subscriptionUserJpaRepository.save(subscriptionUser)
    }

    override fun saveSubscription(subscription: SubscriptionModel): SubscriptionModel {
        return subscriptionCatalogJpaRepository.save(subscription)
    }

    override fun findById(id: Long): SubscriptionUserModel? {
        return subscriptionUserJpaRepository.findByIdAndDeletedAtIsNull(id)
    }

    override fun findSubscriptionByServiceName(serviceName: String): SubscriptionModel? {
        return subscriptionCatalogJpaRepository.findByServiceName(serviceName)
    }

    override fun findByUserId(userId: Long): List<SubscriptionUserModel> {
        return subscriptionUserJpaRepository.findByUserIdAndDeletedAtIsNull(userId)
    }

    override fun findActiveSubscriptionsAtTime(
        userId: Long,
        targetDateTime: ZonedDateTime,
    ): List<SubscriptionUserModel> {
        return subscriptionUserJpaRepository.findActiveSubscriptionsAtTime(userId, targetDateTime)
    }

    override fun countSubscribersByService(): List<Pair<String, Long>> {
        return subscriptionUserJpaRepository.countSubscribersByService()
            .map { Pair(it[0] as String, it[1] as Long) }
    }

    override fun countSubscribersByServiceAndAge(age: Int): List<Pair<String, Long>> {
        return subscriptionUserJpaRepository.countSubscribersByServiceAndAge(age)
            .map { Pair(it[0] as String, it[1] as Long) }
    }
}
