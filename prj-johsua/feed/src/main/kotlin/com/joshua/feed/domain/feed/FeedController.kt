package com.joshua.feed.domain.feed

import com.joshua.feed.domain.content.Content
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/feed")
class FeedController(
    private val feedService: FeedService
) {
    @GetMapping
    suspend fun getFeed(
        @RequestParam username: String, // 인증 미구현 시 임시
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): List<Content> {
        return feedService.getUserFeed(username, page, size)
    }
} 