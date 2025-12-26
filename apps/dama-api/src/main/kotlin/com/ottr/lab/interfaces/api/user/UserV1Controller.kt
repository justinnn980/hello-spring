package com.ottr.lab.interfaces.api.user

import com.ottr.lab.domain.user.UserService
import com.ottr.lab.interfaces.api.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserV1Controller(
    private val userService: UserService,
) : UserV1ApiSpec {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    override fun createUser(
        @Valid @RequestBody request: UserV1Dto.CreateUserRequest,
    ): ApiResponse<UserV1Dto.UserResponse> {
        return userService.createUser(
            email = request.email,
            password = request.password,
            nickname = request.nickname,
        )
            .let { UserV1Dto.UserResponse.from(it) }
            .let { ApiResponse.success(it) }
    }

    @GetMapping("/{id}")
    override fun getUser(
        @PathVariable id: Long,
    ): ApiResponse<UserV1Dto.UserResponse> {
        return userService.getUserById(id)
            .let { UserV1Dto.UserResponse.from(it) }
            .let { ApiResponse.success(it) }
    }

    @PatchMapping("/{id}")
    override fun updateUser(
        @PathVariable id: Long,
        @Valid @RequestBody request: UserV1Dto.UpdateUserRequest,
    ): ApiResponse<UserV1Dto.UserResponse> {
        return userService.updateUser(
            id = id,
            nickname = request.nickname,
        )
            .let { UserV1Dto.UserResponse.from(it) }
            .let { ApiResponse.success(it) }
    }
}
