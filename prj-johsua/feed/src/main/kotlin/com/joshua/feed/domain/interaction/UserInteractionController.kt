package com.joshua.feed.domain.interaction

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/content")
class UserInteractionController(
    private val userInteractionService: UserInteractionService
) {
    @PostMapping("/{id}/read")
    fun readContent(
        @PathVariable id: Long,
        @RequestParam username: String
    ) {
        userInteractionService.saveInteraction(username, id, InteractionType.READ)
    }

    @PostMapping("/{id}/like")
    fun likeContent(
        @PathVariable id: Long,
        @RequestParam username: String
    ) {
        userInteractionService.saveInteraction(username, id, InteractionType.LIKE)
    }

    @PostMapping("/{id}/dislike")
    fun dislikeContent(
        @PathVariable id: Long,
        @RequestParam username: String
    ) {
        userInteractionService.saveInteraction(username, id, InteractionType.DISLIKE)
    }
} 