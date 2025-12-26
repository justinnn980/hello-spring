package com.ottr.lab.interfaces.api.post

import com.ottr.lab.interfaces.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Post V1 API", description = "게시물 관리 API")
interface PostV1ApiSpec {
    @Operation(
        summary = "게시물 생성",
        description = "새로운 게시물을 생성합니다.",
    )
    fun createPost(
        @Schema(description = "작성자 이메일")
        email: String,
        @Schema(description = "게시물 생성 요청 정보")
        request: PostV1Dto.CreatePostRequest,
    ): ApiResponse<PostV1Dto.PostResponse>

    @Operation(
        summary = "게시물 목록 조회",
        description = "모든 게시물의 목록을 조회합니다.",
    )
    fun getPosts(): ApiResponse<List<PostV1Dto.PostResponse>>

    @Operation(
        summary = "게시물 조회",
        description = "ID로 게시물을 조회합니다.",
    )
    fun getPost(
        @Schema(description = "게시물 ID", example = "1")
        id: Long,
    ): ApiResponse<PostV1Dto.PostResponse>

    @Operation(
        summary = "게시물 수정",
        description = "ID로 게시물을 수정합니다.",
    )
    fun updatePost(
        @Schema(description = "작성자 이메일")
        email: String,
        @Schema(description = "게시물 ID", example = "1")
        id: Long,
        @Schema(description = "게시물 수정 요청 정보")
        request: PostV1Dto.UpdatePostRequest,
    ): ApiResponse<PostV1Dto.PostResponse>

    @Operation(
        summary = "게시물 삭제",
        description = "ID로 게시물을 삭제합니다.",
    )
    fun deletePost(
        @Schema(description = "게시물 ID", example = "1")
        id: Long,
    ): ApiResponse<Unit>
}
