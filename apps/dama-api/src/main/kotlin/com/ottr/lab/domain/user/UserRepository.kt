package com.ottr.lab.domain.user

interface UserRepository {
    fun save(user: UserModel): UserModel
    fun findById(id: Long): UserModel?
    fun existsByEmail(email: String): Boolean
}