package com.ottr.lab.domain.user

import com.ottr.lab.support.error.CoreException
import com.ottr.lab.support.error.ErrorType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun createUser(email: String, password: String, nickname: String?): UserModel {
        if (userRepository.existsByEmail(email)) {
            throw CoreException(ErrorType.CONFLICT, "이미 존재하는 이메일입니다.")
        }

        val user = UserModel(
            email = email,
            password = password,
            nickname = nickname,
        )

        return userRepository.save(user)
    }

    @Transactional(readOnly = true)
    fun getUserById(id: Long): UserModel {
        return userRepository.findById(id)
            ?: throw CoreException(ErrorType.NOT_FOUND, "사용자를 찾을 수 없습니다.")
    }

    @Transactional
    fun updateUser(id: Long, nickname: String?): UserModel {
        val user = userRepository.findById(id)
            ?: throw CoreException(ErrorType.NOT_FOUND, "사용자를 찾을 수 없습니다.")

        user.updateNickname(nickname)

        return userRepository.save(user)
    }
}
