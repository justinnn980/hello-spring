package com.ottr.lab.interfaces.api.post

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

class PostV1Dto {
    data class CreatePostRequest(
        @field:NotBlank(message = "제목은 필수입니다.")
        @field:Size(max = 200, message = "제목은 200자를 초과할 수 없습니다.")
        val title: String,

        @field:NotBlank(message = "내용은 필수입니다.")
        @field:Size(max = 5000, message = "내용은 5000자를 초과할 수 없습니다.")
        val content: String,
    )

    data class UpdatePostRequest(
        @field:NotBlank(message = "제목은 필수입니다.")
        @field:Size(max = 200, message = "제목은 200자를 초과할 수 없습니다.")
        val title: String,

        @field:NotBlank(message = "내용은 필수입니다.")
        @field:Size(max = 5000, message = "내용은 5000자를 초과할 수 없습니다.")
        val content: String,
    )

    data class PostResponse(
        val id: Long,
        val categoryId: Long,
        val category: String,
        val title: String,
        val content: String,
        val viewCount: Int,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
    )
}
