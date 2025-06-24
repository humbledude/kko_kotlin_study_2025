package com.joshua.feed.domain.interaction

import com.joshua.feed.domain.content.ContentRepository
import com.joshua.feed.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserInteractionService(
    private val userRepository: UserRepository,
    private val contentRepository: ContentRepository,
    private val userInteractionRepository: UserInteractionRepository
) {
    @Transactional
    fun saveInteraction(username: String, contentId: Long, type: InteractionType) {
        val user = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다.")
        val content = contentRepository.findById(contentId)
            .orElseThrow { IllegalArgumentException("컨텐츠를 찾을 수 없습니다.") }
        val interaction = UserInteractionEntity(
            user = user,
            content = content,
            interactionType = type
        )
        userInteractionRepository.save(interaction)
    }
} 