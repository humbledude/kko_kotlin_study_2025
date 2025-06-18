package com.joshua.feed.domain.feed

import com.joshua.feed.domain.content.Content
import org.springframework.stereotype.Service

@Service
class FeedService {
    suspend fun getUserFeed(username: String, page: Int, size: Int): List<Content> {
        // TODO: 사용자별 피드 조회, 중복 컨텐츠 방지, 페이징 처리 구현
        return emptyList()
    }
} 