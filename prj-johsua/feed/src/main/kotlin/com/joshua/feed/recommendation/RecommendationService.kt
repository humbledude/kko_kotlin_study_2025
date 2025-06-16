package com.joshua.feed.recommendation

import com.joshua.feed.domain.content.Content
import com.joshua.feed.domain.user.User
import org.springframework.stereotype.Service

@Service
class RecommendationService(
    private val recommendationEngine: RecommendationEngine
) {
    /**
     * 사용자에게 추천 컨텐츠 ID를 제공합니다.
     * @param user 추천을 받을 사용자
     * @param count 추천할 컨텐츠의 개수
     * @return 추천된 컨텐츠 ID 목록
     */
    suspend fun getRecommendedIds(user: User, count: Int): List<String> {
        return recommendationEngine.getRecommendedIds(user, count)
    }
} 