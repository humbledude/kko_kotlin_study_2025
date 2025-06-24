package com.joshua.feed.pokemon

import com.joshua.feed.pokemon.model.Pokemon
import com.joshua.feed.pokemon.model.PokemonNotFoundException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PokeApiServiceTest {
    private val service = mockk<PokeApiService>()

    @Test
    fun `getPokemon returns Pokemon when API call is successful`() = runBlocking {
        // Given
        val pokemonId = 1
        val expectedPokemon = Pokemon(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = mockk(),
            stats = emptyList()
        )
        coEvery { service.getPokemon(pokemonId) } returns expectedPokemon

        // When
        val result = service.getPokemon(pokemonId)

        // Then
        assertNotNull(result)
        assertEquals(expectedPokemon.id, result.id)
        assertEquals(expectedPokemon.name, result.name)
    }

    @Test
    fun `getPokemon throws PokemonNotFoundException when Pokemon is not found`() = runBlocking {
        // Given
        val pokemonId = 99999
        coEvery { service.getPokemon(pokemonId) } throws PokemonNotFoundException("Pokemon $pokemonId is not found")

        // When & Then
        assertThrows<PokemonNotFoundException> {
            service.getPokemon(pokemonId)
        }
    }
} 