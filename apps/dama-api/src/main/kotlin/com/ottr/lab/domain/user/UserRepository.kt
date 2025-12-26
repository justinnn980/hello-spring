package com.ottr.lab.domain.user

interface UserRepository {
    fun save(user: UserModel): UserModel
    fun findById(id: Long): UserModel?
    fun findByEmail(email: String): UserModel?
    fun existsByEmail(email: String): Boolean
}
