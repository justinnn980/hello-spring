package com.ottr.lab.utils

import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Table
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DatabaseCleanUp(
    @PersistenceContext private val entityManager: EntityManager,
) : InitializingBean {
    private val tableNames = mutableListOf<String>()

    override fun afterPropertiesSet() {
        entityManager.metamodel.entities
            .filter { entity -> entity.javaType.isAnnotationPresent(Entity::class.java) }
            .mapNotNull { entity ->
                val tableAnnotation = entity.javaType.getAnnotation(Table::class.java)
                val name = tableAnnotation?.name
                when {
                    !name.isNullOrBlank() -> name
                    else -> entity.name // 필요하면 entity.javaType.simpleName.lowercase() 등으로 맞춰도 됨
                }
            }
            .forEach { tableNames.add(it) }
    }

    @Transactional
    fun truncateAllTables() {
        entityManager.flush()
        if (tableNames.isEmpty()) return
        val truncateSql = tableNames.joinToString(", ") { it }
        entityManager
            .createNativeQuery("TRUNCATE TABLE $truncateSql RESTART IDENTITY CASCADE")
            .executeUpdate()
    }
}
