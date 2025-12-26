package com.ottr.lab.application.post

import com.ottr.lab.domain.post.PostModel

data class PostInfo(
    val id: Long,
    val title: String,
    val content: String,
    val userId: Long,
    val categoryId: Long,
) {
    companion object {
        fun from(model: PostModel): PostInfo = PostInfo(
            id = model.id,
            title = model.title,
            content = model.content,
            userId = model.userId,
            categoryId = model.categoryId,
        )
    }
}
