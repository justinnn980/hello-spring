package com.ottr.lab.domain.user

import com.ottr.lab.domain.BaseEntity
import com.ottr.lab.support.error.CoreException
import com.ottr.lab.support.error.ErrorType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
@Table(name = "users")
class UserModel(
    email: String,
    password: String,
    nickname: String? = null,
    birth: String? = null,
    age: Int? = null,
) : BaseEntity() {
    @Column(name = "email", nullable = false, unique = true, length = 255)
    var email: String = email
        protected set

    @Column(name = "password_hash", nullable = false, length = 255)
    var passwordHash: String = hashPassword(password)
        protected set

    @Column(name = "nickname", length = 100)
    var nickname: String? = nickname
        protected set

    @Column(name = "birth")
    var birth: String? = birth
        protected set

    @Column(name = "age")
    var age: Int? = age
        protected set

    init {
        validateEmail(email)
        validatePassword(password)
        validateNickname(nickname)
    }

    fun updateNickname(newNickname: String?) {
        validateNickname(newNickname)
        this.nickname = newNickname
    }

    private fun validateEmail(email: String) {
        if (email.isBlank()) {
            throw CoreException(ErrorType.BAD_REQUEST, "이메일은 필수입니다.")
        }
        if (email.length > 255) {
            throw CoreException(ErrorType.BAD_REQUEST, "이메일은 255자를 초과할 수 없습니다.")
        }
        if (!email.contains("@")) {
            throw CoreException(ErrorType.BAD_REQUEST, "유효한 이메일 형식이 아닙니다.")
        }
    }

    private fun validatePassword(password: String) {
        if (password.isBlank()) {
            throw CoreException(ErrorType.BAD_REQUEST, "비밀번호는 필수입니다.")
        }
        if (password.length < 8) {
            throw CoreException(ErrorType.BAD_REQUEST, "비밀번호는 최소 8자 이상이어야 합니다.")
        }
    }

    private fun validateNickname(nickname: String?) {
        if (nickname != null && nickname.length > 100) {
            throw CoreException(ErrorType.BAD_REQUEST, "닉네임은 100자를 초과할 수 없습니다.")
        }
    }

    companion object {
        private val passwordEncoder = BCryptPasswordEncoder()

        private fun hashPassword(plainPassword: String): String {
            return passwordEncoder.encode(plainPassword)
        }
    }
}
