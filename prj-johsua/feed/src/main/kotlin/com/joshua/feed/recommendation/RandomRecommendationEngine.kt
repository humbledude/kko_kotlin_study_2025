package com.joshua.feed.recommendation

import com.joshua.feed.pokemon.PokemonContentProvider
import com.joshua.feed.domain.user.UserEntity
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class RandomRecommendationEngine : RecommendationEngine {
    // 사용자별 이미 추천한 컨텐츠 ID를 저장 (실제로는 DB나 Redis 사용)
    private val userSeenContent = mutableMapOf<String, MutableSet<Long>>()
    
    override suspend fun getRecommendedIds(user: UserEntity, count: Int): List<Long> {
        val maxId = PokemonContentProvider.MAX_POKEMON_ID
        if (count > maxId) throw IllegalArgumentException("count가 MAX_POKEMON_ID보다 큽니다.")
        
        // 사용자가 이미 본 컨텐츠 ID 목록 가져오기
        val seenIds = userSeenContent.getOrPut(user.username) { mutableSetOf() }
        
        // 아직 보지 않은 ID들 중에서 랜덤 선택
        val unseenIds = (1L..maxId).filter { it !in seenIds }
        
        if (unseenIds.isEmpty()) {
            // 모든 컨텐츠를 다 본 경우 초기화
            seenIds.clear()
            return (1L..maxId).shuffled().take(count)
        }
        
        // 요청한 수만큼 랜덤 선택
        val selectedIds = unseenIds.shuffled().take(count)
        
        // 선택된 ID들을 본 것으로 표시
        seenIds.addAll(selectedIds)
        
        return selectedIds
    }
} 