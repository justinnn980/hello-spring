package com.ottr.lab.domain.subscription

import java.time.ZonedDateTime

/**
 * 구독 정보의 영속성을 관리하는 Repository 인터페이스
 */
interface SubscriptionRepository {
    /**
     * 사용자별 구독 정보를 저장합니다.
     *
     * @param subscriptionUser 저장할 사용자 구독 정보
     * @return 저장된 사용자 구독 정보
     */
    fun save(subscriptionUser: SubscriptionUserModel): SubscriptionUserModel

    /**
     * 구독 서비스를 저장합니다.
     *
     * @param subscription 저장할 구독 서비스 정보
     * @return 저장된 구독 서비스 정보
     */
    fun saveSubscription(subscription: SubscriptionModel): SubscriptionModel

    /**
     * ID로 구독 정보를 조회합니다. (soft delete된 항목 제외)
     *
     * @param id 구독 ID
     * @return 구독 정보, 없으면 null
     */
    fun findById(id: Long): SubscriptionUserModel?

    /**
     * 서비스명으로 구독 서비스를 조회합니다.
     *
     * @param serviceName 서비스명
     * @return 구독 서비스 정보, 없으면 null
     */
    fun findSubscriptionByServiceName(serviceName: String): SubscriptionModel?

    /**
     * 사용자 ID로 구독 목록을 조회합니다. (soft delete된 항목 제외)
     *
     * @param userId 사용자 ID
     * @return 구독 목록 (최신 생성 순으로 정렬)
     */
    fun findByUserId(userId: Long): List<SubscriptionUserModel>

    /**
     * 특정 시점 기준으로 활성화되어 있던 구독 목록을 조회합니다.
     * (해당 시점에 생성되어 있고, 삭제되지 않았거나 삭제 시점이 그 이후인 구독)
     *
     * @param userId 사용자 ID
     * @param targetDateTime 기준 시점
     * @return 구독 목록
     */
    fun findActiveSubscriptionsAtTime(userId: Long, targetDateTime: ZonedDateTime): List<SubscriptionUserModel>

    /**
     * 서비스별 구독자 수를 조회합니다. (soft delete된 항목 제외)
     *
     * @return 서비스별 구독자 수 목록 (구독자 수 내림차순)
     */
    fun countSubscribersByService(): List<Pair<String, Long>>

    /**
     * 특정 연령대의 서비스별 구독자 수를 조회합니다.
     *
     * @param age 연령대 (10, 20, 30, ...)
     * @return 서비스별 구독자 수 목록 (구독자 수 내림차순)
     */
    fun countSubscribersByServiceAndAge(age: Int): List<Pair<String, Long>>
}
