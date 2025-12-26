package com.ottr.lab.domain.user

import com.ottr.lab.support.error.CoreException
import com.ottr.lab.support.error.ErrorType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun createUser(email: String, password: String, nickname: String?, birth: String): UserModel {
        if (userRepository.existsByEmail(email)) {
            throw CoreException(ErrorType.CONFLICT, "이미 존재하는 이메일입니다.")
        }

        val birthDate = parseBirthDate(birth)
        val age = calculateAgeGroup(birthDate)

        val user = UserModel(
            email = email,
            password = password,
            nickname = nickname,
            birth = birthDate,
            age = age,
        )

        return userRepository.save(user)
    }

    private fun parseBirthDate(birth: String): LocalDate {
        return try {
            LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyyMMdd"))
        } catch (e: DateTimeParseException) {
            throw CoreException(ErrorType.BAD_REQUEST, "생년월일 형식이 올바르지 않습니다.")
        }
    }

    private fun calculateAgeGroup(birthDate: LocalDate): Int {
        val currentYear = Year.now().value
        val age = currentYear - birthDate.year

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
