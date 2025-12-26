package com.ottr.lab.domain.post

interface PostRepository {
    fun save(post: PostModel): PostModel
    fun existsById(id: Long): Boolean
    fun findAll(): List<PostModel>
    fun findById(id: Long): PostModel?
}
