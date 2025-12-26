package com.ottr.lab.domain.post

import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
)
