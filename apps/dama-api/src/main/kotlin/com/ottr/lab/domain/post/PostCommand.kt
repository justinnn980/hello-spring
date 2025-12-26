package com.ottr.lab.domain.post

class PostCommand {
    data class CreatePost(
        val title: String,
        val content: String,
        val userId: Long,
        val categoryId: Long,
    )
}
