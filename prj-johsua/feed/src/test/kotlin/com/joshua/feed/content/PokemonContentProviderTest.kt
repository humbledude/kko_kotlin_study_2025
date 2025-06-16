package com.joshua.feed.content

import com.fasterxml.jackson.databind.node.ArrayNode
import com.joshua.feed.domain.content.Content
import com.joshua.feed.pokemon.PokeApiService
import com.joshua.feed.pokemon.model.Pokemon
import com.joshua.feed.pokemon.model.PokemonSprite
import com.joshua.feed.pokemon.model.PokemonStat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PokemonContentProviderTest {
    private val pokeApiService = mockk<PokeApiService>()
    private val provider = PokemonContentProvider(pokeApiService)

    @Test
    fun `getContent returns Content with correct data`() = runBlocking {
        // Given
        val pokemonId = "1"
        val pokemon = Pokemon(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = PokemonSprite(
                frontDefault = "https://example.com/bulbasaur.png",
                backDefault = "https://example.com/bulbasaur-back.png"
            ),
            stats = listOf(
                PokemonStat(45, mockk(relaxed = true)),
                PokemonStat(49, mockk(relaxed = true))
            )
        )
        coEvery { pokeApiService.getPokemon(1) } returns pokemon

        // When
        val content: Content = provider.getContent(pokemonId)

        // Then
        assertNotNull(content)
        assertEquals("Bulbasaur", content.title)
        assertEquals("포켓몬 #1 - Bulbasaur", content.description)
        assertEquals("https://example.com/bulbasaur.png", content.imageUrl)
        assertEquals("POKEMON", content.contentType)
        assertEquals(7, content.body["height"].asInt())
        val statsNode = content.body["stats"] as ArrayNode
        assertEquals(2, statsNode.size())
        assertEquals(45, statsNode[0]["baseStat"].asInt())
        assertEquals(49, statsNode[1]["baseStat"].asInt())
    }

    @Test
    fun `getRandomContent returns a random Content`() = runBlocking {
        // Given
        val pokemon = Pokemon(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = PokemonSprite(
                frontDefault = "https://example.com/bulbasaur.png",
                backDefault = "https://example.com/bulbasaur-back.png"
            ),
            stats = listOf(
                PokemonStat(45, mockk(relaxed = true)),
                PokemonStat(49, mockk(relaxed = true))
            )
        )
        coEvery { pokeApiService.getPokemon(any()) } returns pokemon

        // When
        val content: Content = provider.getRandomContent()

        // Then
        assertNotNull(content)
        assertEquals("Bulbasaur", content.title)
        assertEquals("POKEMON", content.contentType)
    }

    @Test
    fun `getContents returns a list of Content`() = runBlocking {
        // Given
        val pokemon = Pokemon(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = PokemonSprite(
                frontDefault = "https://example.com/bulbasaur.png",
                backDefault = "https://example.com/bulbasaur-back.png"
            ),
            stats = listOf(
                PokemonStat(45, mockk(relaxed = true)),
                PokemonStat(49, mockk(relaxed = true))
            )
        )
        coEvery { pokeApiService.getPokemon(any()) } returns pokemon

        // When
        val contents: List<Content> = provider.getContents(3)

        // Then
        assertEquals(3, contents.size)
        contents.forEach { content ->
            assertEquals("Bulbasaur", content.title)
            assertEquals("POKEMON", content.contentType)
        }
    }
} 