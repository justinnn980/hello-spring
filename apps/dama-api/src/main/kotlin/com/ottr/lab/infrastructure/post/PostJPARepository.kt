package com.ottr.lab.infrastructure.post

import com.ottr.lab.domain.post.PostModel
import org.springframework.data.jpa.repository.JpaRepository

interface PostJPARepository : JpaRepository<PostModel, Long>
