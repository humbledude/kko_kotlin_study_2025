package com.joshua.feed.pokemon

import com.joshua.feed.domain.content.Content
import com.joshua.feed.domain.content.ContentProvider
import com.joshua.feed.domain.user.UserEntity
import com.joshua.feed.recommendation.RecommendationEngine
import org.springframework.stereotype.Service

@Service
class PokemonContentProvider(
    private val pokeApiService: PokeApiService,
    private val recommendationEngine: RecommendationEngine
) : ContentProvider {
    
    companion object {
        const val MAX_POKEMON_ID = 898L  // 현재 포켓몬 API에서 사용 가능한 최대 ID
        private val ANONYMOUS_USER = UserEntity(username = "anonymous", password = "", email = "")
    }

    override suspend fun getContent(id: Long): Content {
        val pokemon = pokeApiService.getPokemon(id.toInt())
        return PokemonContent(pokemon).content
    }

    override suspend fun getContents(count: Int): List<Content> {
        // 추천 엔진으로부터 ID 목록을 받아와서 컨텐츠를 조회
        val recommendedIds = recommendationEngine.getRecommendedIds(ANONYMOUS_USER, count)
        return recommendedIds.map { id ->
            getContent(id)
        }
    }
} 