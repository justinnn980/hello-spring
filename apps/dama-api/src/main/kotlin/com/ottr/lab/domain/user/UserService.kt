package com.ottr.lab.domain.user

import com.ottr.lab.support.error.CoreException
import com.ottr.lab.support.error.ErrorType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Year

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun createUser(email: String, password: String, nickname: String?, birth: String?): UserModel {
        if (userRepository.existsByEmail(email)) {
            throw CoreException(ErrorType.CONFLICT, "이미 존재하는 이메일입니다.")
        }

        val age = birth?.let { calculateAgeGroup(it) }

        val user = UserModel(
            email = email,
            password = password,
            nickname = nickname,
            birth = birth,
            age = age,
        )

        return userRepository.save(user)
    }

    private fun calculateAgeGroup(birth: String): Int {
        if (birth.length < 4) {
            throw CoreException(ErrorType.BAD_REQUEST, "생년월일 형식이 올바르지 않습니다.")
        }

        val birthYear = birth.substring(0, 4).toIntOrNull()
            ?: throw CoreException(ErrorType.BAD_REQUEST, "생년월일 형식이 올바르지 않습니다.")

        val currentYear = Year.now().value
        val age = currentYear - birthYear

        return (age / 10) * 10
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
