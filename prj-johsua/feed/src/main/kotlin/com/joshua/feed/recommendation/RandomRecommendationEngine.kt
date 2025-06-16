package com.joshua.feed.recommendation

import com.joshua.feed.content.PokemonContentProvider
import com.joshua.feed.domain.user.User
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class RandomRecommendationEngine : RecommendationEngine {
    override suspend fun getRecommendedIds(user: User, count: Int): List<String> {
        return (1..count).map {
            Random.nextInt(1, PokemonContentProvider.MAX_POKEMON_ID + 1).toString()
        }
    }
} 