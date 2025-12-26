package com.ottr.lab.domain.post

import com.ottr.lab.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "posts")
class PostModel(
    userId: Long,
    categoryId: Long,
    title: String,
    content: String,
    viewCount: Int = 0,
) : BaseEntity() {
    @Column(name = "user_id", nullable = false)
    var userId: Long = userId
        protected set

    @Column()
    var categoryId: Long = categoryId
        protected set

    var title: String = title
        protected set

    var content: String = content
        protected set

    var viewCount: Int = viewCount
        protected set

    private fun validate() {
    }

    init {
        validate()
    }

    override fun guard() {
        validate()
    }
}
