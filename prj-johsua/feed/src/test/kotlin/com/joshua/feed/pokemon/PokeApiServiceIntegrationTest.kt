package com.joshua.feed.pokemon

import com.joshua.feed.pokemon.model.Pokemon
import com.joshua.feed.pokemon.model.PokemonNotFoundException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
class PokeApiServiceIntegrationTest {

    @Autowired
    private lateinit var pokeApiService: PokeApiService

    @Test
    fun `실제 API 호출로 bulbasaur 정보를 가져온다`() = runBlocking {
        // Given
        val pokemonId = 1

        // When
        val result = pokeApiService.getPokemon(pokemonId)

        // Then
        assertNotNull(result)
        assertEquals(1, result.id)
        assertEquals("bulbasaur", result.name)
        assertNotNull(result.sprites)
        assertNotNull(result.stats)
        assert(result.stats.isNotEmpty())
    }

    @Test
    fun `존재하지 않는 포켓몬 ID로 API 호출시 예외가 발생한다`() = runBlocking {
        // Given
        val pokemonId = 99999

        // When & Then
        assertThrows<PokemonNotFoundException> {
            pokeApiService.getPokemon(pokemonId)
        }
    }
} 