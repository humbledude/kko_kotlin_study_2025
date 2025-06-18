package com.joshua.feed.recommendation

import com.joshua.feed.pokemon.PokemonContentProvider
import com.joshua.feed.domain.user.UserEntity
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class RandomRecommendationEngine : RecommendationEngine {
    override suspend fun getRecommendedIds(user: UserEntity, count: Int): List<Long> {
        return (1..count).map {
            Random.nextLong(1, PokemonContentProvider.MAX_POKEMON_ID + 1)
        }
    }
} 