package com.ottr.lab.infrastructure.user

import com.ottr.lab.domain.user.UserModel
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserModel, Long> {
    fun existsByEmail(email: String): Boolean
}