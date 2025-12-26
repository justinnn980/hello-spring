package com.ottr.lab.domain.post

import com.ottr.lab.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "post_categories")
class PostCategory(
    name: String,
    slug: String,
    isActive: Boolean,
    sortOrder: Int,
) : BaseEntity()
