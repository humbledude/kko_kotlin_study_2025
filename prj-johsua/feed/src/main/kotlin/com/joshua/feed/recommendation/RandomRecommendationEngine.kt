package com.joshua.feed.recommendation

import com.joshua.feed.pokemon.PokemonContentProvider
import com.joshua.feed.domain.user.UserEntity
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class RandomRecommendationEngine : RecommendationEngine {
    override suspend fun getRecommendedIds(user: UserEntity, count: Int): List<Long> {
        // 중복 없는 랜덤 숫자 생성
        val maxId = PokemonContentProvider.MAX_POKEMON_ID
        if (count > maxId) throw IllegalArgumentException("count가 MAX_POKEMON_ID보다 큽니다.")
        return (1L..maxId).shuffled().take(count)
    }
} 