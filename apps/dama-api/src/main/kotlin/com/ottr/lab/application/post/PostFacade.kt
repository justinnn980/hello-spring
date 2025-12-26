package com.ottr.lab.application.post

import com.ottr.lab.domain.post.PostService
import com.ottr.lab.domain.user.UserService
import org.springframework.stereotype.Component

@Component
class PostFacade(
    private val userService: UserService,
    private val postService: PostService,
) {
    fun createPost(
        email: String,

    ) {}
    fun getPosts() {}
    fun getPost(id: Long) {}
    fun updatePost(id: Long) {}
    fun deletePost(id: Long) {}
}
