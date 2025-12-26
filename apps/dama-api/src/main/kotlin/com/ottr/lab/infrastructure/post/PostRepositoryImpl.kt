package com.ottr.lab.infrastructure.post

import com.ottr.lab.domain.post.PostModel
import com.ottr.lab.domain.post.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl(
    private val postJPARepository: PostJPARepository,
) : PostRepository {
    override fun save(post: PostModel): PostModel = postJPARepository.save(post)
    override fun existsById(id: Long): Boolean = postJPARepository.existsById(id)
    override fun findAll(): List<PostModel> = postJPARepository.findAll()
    override fun findById(id: Long): PostModel? = postJPARepository.findByIdOrNull(id)
}
