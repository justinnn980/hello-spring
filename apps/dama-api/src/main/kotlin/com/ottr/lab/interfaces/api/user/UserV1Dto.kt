package com.ottr.lab.interfaces.api.user

import com.ottr.lab.domain.user.UserModel
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.ZonedDateTime

class UserV1Dto {
    data class CreateUserRequest(
        @field:NotBlank(message = "이메일은 필수입니다.")
        @field:Email(message = "유효한 이메일 형식이 아닙니다.")
        @field:Size(max = 255, message = "이메일은 255자를 초과할 수 없습니다.")
        val email: String,

        @field:NotBlank(message = "비밀번호는 필수입니다.")
        @field:Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        val password: String,

        @field:Size(max = 100, message = "닉네임은 100자를 초과할 수 없습니다.")
        val nickname: String? = null,
    )

    data class UpdateUserRequest(
        @field:Size(max = 100, message = "닉네임은 100자를 초과할 수 없습니다.")
        val nickname: String?,
    )

    data class UserResponse(
        val id: Long,
        val email: String,
        val nickname: String?,
        val createdAt: ZonedDateTime,
        val updatedAt: ZonedDateTime,
    ) {
        companion object {
            fun from(user: UserModel): UserResponse {
                return UserResponse(
                    id = user.id,
                    email = user.email,
                    nickname = user.nickname,
                    createdAt = user.createdAt,
                    updatedAt = user.updatedAt,
                )
            }
        }
    }
}
