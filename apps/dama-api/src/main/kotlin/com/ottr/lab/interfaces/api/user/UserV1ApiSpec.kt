package com.ottr.lab.interfaces.api.user

import com.ottr.lab.interfaces.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "User V1 API", description = "사용자 관리 API")
interface UserV1ApiSpec {
    @Operation(
        summary = "회원가입",
        description = "새로운 사용자를 생성합니다. 이메일은 중복될 수 없으며, 비밀번호는 BCrypt로 해싱되어 저장됩니다.",
    )
    fun createUser(
        @Schema(description = "회원가입 요청 정보")
        request: UserV1Dto.CreateUserRequest,
    ): ApiResponse<UserV1Dto.UserResponse>

    @Operation(
        summary = "사용자 조회",
        description = "ID로 사용자 정보를 조회합니다.",
    )
    fun getUser(
        @Schema(description = "사용자 ID", example = "1")
        id: Long,
    ): ApiResponse<UserV1Dto.UserResponse>

    @Operation(
        summary = "사용자 정보 수정",
        description = "사용자의 닉네임을 수정합니다.",
    )
    fun updateUser(
        @Schema(description = "사용자 ID", example = "1")
        id: Long,
        @Schema(description = "사용자 정보 수정 요청")
        request: UserV1Dto.UpdateUserRequest,
    ): ApiResponse<UserV1Dto.UserResponse>
}
