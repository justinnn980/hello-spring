package com.ottr.lab.infrastructure.subscription

import com.ottr.lab.domain.subscription.SubscriptionUserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.ZonedDateTime

/**
 * Spring Data JPA를 활용한 사용자별 구독 Repository
 */
interface SubscriptionUserJpaRepository : JpaRepository<SubscriptionUserModel, Long> {
    /**
     * 사용자 ID로 구독 목록을 조회합니다. (soft delete된 항목 제외)
     * 최신 생성 순으로 정렬
     *
     * @param userId 사용자 ID
     * @return 구독 목록
     */
    @Query(
        """
        SELECT su FROM SubscriptionUserModel su
        JOIN FETCH su.subscription s
        WHERE su.userId = :userId AND su.deletedAt IS NULL
        ORDER BY su.createdAt DESC
        """,
    )
    fun findByUserIdAndDeletedAtIsNull(@Param("userId") userId: Long): List<SubscriptionUserModel>

    /**
     * ID로 구독 정보를 조회합니다. (soft delete된 항목 제외)
     *
     * @param id 구독 ID
     * @return 구독 정보, 없으면 null
     */
    @Query("SELECT su FROM SubscriptionUserModel su WHERE su.id = :id AND su.deletedAt IS NULL")
    fun findByIdAndDeletedAtIsNull(@Param("id") id: Long): SubscriptionUserModel?

    /**
     * 특정 시점 기준으로 활성화되어 있던 구독 목록을 조회합니다.
     *
     * @param userId 사용자 ID
     * @param targetDateTime 기준 시점
     * @return 구독 목록
     */
    @Query(
        """
        SELECT su FROM SubscriptionUserModel su
        JOIN FETCH su.subscription s
        WHERE su.userId = :userId
        AND su.createdAt <= :targetDateTime
        AND (su.deletedAt IS NULL OR su.deletedAt > :targetDateTime)
        """,
    )
    fun findActiveSubscriptionsAtTime(
        @Param("userId") userId: Long,
        @Param("targetDateTime") targetDateTime: ZonedDateTime,
    ): List<SubscriptionUserModel>

    /**
     * 서비스별 구독자 수를 조회합니다. (soft delete 제외)
     *
     * @return 서비스별 구독자 수 목록 (구독자 수 내림차순)
     */
    @Query(
        """
        SELECT s.serviceName, COUNT(DISTINCT su.userId)
        FROM SubscriptionUserModel su
        JOIN su.subscription s
        WHERE su.deletedAt IS NULL
        GROUP BY s.serviceName
        ORDER BY COUNT(DISTINCT su.userId) DESC
        """,
    )
    fun countSubscribersByService(): List<Array<Any>>

    /**
     * 특정 연령대의 서비스별 구독자 수를 조회합니다.
     *
     * @param age 연령대
     * @return 서비스별 구독자 수 목록 (구독자 수 내림차순)
     */
    @Query(
        """
        SELECT s.serviceName, COUNT(DISTINCT su.userId)
        FROM SubscriptionUserModel su
        JOIN su.subscription s
        JOIN UserModel u ON su.userId = u.id
        WHERE su.deletedAt IS NULL AND u.age = :age
        GROUP BY s.serviceName
        ORDER BY COUNT(DISTINCT su.userId) DESC
        """,
    )
    fun countSubscribersByServiceAndAge(@Param("age") age: Int): List<Array<Any>>
}
