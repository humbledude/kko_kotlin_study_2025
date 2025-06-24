package com.joshua.feed.recommendation

import com.joshua.feed.pokemon.PokemonContentProvider
import com.joshua.feed.domain.user.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class RandomRecommendationEngineTest {

    private val engine = RandomRecommendationEngine()

    @Test
    fun `getRecommendedIds는 요청한 개수만큼 ID를 반환한다`() = runBlocking {
        // given
        val user = UserEntity(username = "test", password = "test", email = "test@test.com")
        val count = 5

        // when
        val result = engine.getRecommendedIds(user, count)

        // then
        assertEquals(count, result.size)
        result.forEach { id ->
            assertTrue(id.toInt() in 1..PokemonContentProvider.MAX_POKEMON_ID)
        }
    }

    @Test
    fun `getRecommendedIds는 중복되지 않은 ID를 반환한다`() = runBlocking {
        // given
        val user = UserEntity(username = "test", password = "test", email = "test@test.com")
        val count = 10

        // when
        val result = engine.getRecommendedIds(user, count)

        // then
        assertEquals(count, result.distinct().size)
    }
} 