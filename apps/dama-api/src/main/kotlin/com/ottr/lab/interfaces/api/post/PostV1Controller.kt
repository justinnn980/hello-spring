package com.ottr.lab.interfaces.api.post

import com.ottr.lab.interfaces.api.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/posts")
class PostV1Controller() : PostV1ApiSpec {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    override fun createPost(
        @RequestHeader("X-USER-ID") email: String,
        @RequestBody request: PostV1Dto.CreatePostRequest,
    ): ApiResponse<PostV1Dto.PostResponse> {
        TODO("Not yet implemented")
    }

    @GetMapping
    override fun getPosts(): ApiResponse<List<PostV1Dto.PostResponse>> {
        TODO("Not yet implemented")
    }

    @GetMapping("/{id}")
    override fun getPost(
        @PathVariable id: Long,
    ): ApiResponse<PostV1Dto.PostResponse> {
        TODO("Not yet implemented")
    }

    @PatchMapping("/{id}")
    override fun updatePost(
        @RequestHeader("X-USER-ID") email: String,
        @PathVariable id: Long,
        @RequestBody request: PostV1Dto.UpdatePostRequest,
    ): ApiResponse<PostV1Dto.PostResponse> {
        TODO("Not yet implemented")
    }

    @DeleteMapping("/{id}")
    override fun deletePost(
        @PathVariable id: Long,
    ): ApiResponse<Unit> {
        TODO("Not yet implemented")
    }
}
