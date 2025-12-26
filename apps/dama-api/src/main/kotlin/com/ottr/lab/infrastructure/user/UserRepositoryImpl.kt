package com.ottr.lab.infrastructure.user

import com.ottr.lab.domain.user.UserModel
import com.ottr.lab.domain.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun save(user: UserModel): UserModel {
        return userJpaRepository.save(user)
    }

    override fun findById(id: Long): UserModel? {
        return userJpaRepository.findByIdOrNull(id)
    }

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }
}