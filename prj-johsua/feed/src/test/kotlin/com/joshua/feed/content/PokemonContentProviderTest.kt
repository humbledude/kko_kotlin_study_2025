package com.joshua.feed.content

import com.fasterxml.jackson.databind.node.ArrayNode
import com.joshua.feed.domain.content.Content
import com.joshua.feed.domain.user.User
import com.joshua.feed.pokemon.PokeApiService
import com.joshua.feed.pokemon.model.Pokemon
import com.joshua.feed.pokemon.model.PokemonSprite
import com.joshua.feed.pokemon.model.PokemonStat
import com.joshua.feed.pokemon.model.Stat
import com.joshua.feed.recommendation.RecommendationEngine
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PokemonContentProviderTest {

    private lateinit var pokeApiService: PokeApiService
    private lateinit var recommendationEngine: RecommendationEngine
    private lateinit var provider: PokemonContentProvider

    @BeforeEach
    fun setup() {
        pokeApiService = mockk()
        recommendationEngine = mockk()
        provider = PokemonContentProvider(pokeApiService, recommendationEngine)
    }

    @Test
    fun `getContent는 포켓몬 ID로 컨텐츠를 조회한다`() = runBlocking {
        // given
        val pokemonId = "1"
        val pokemon = Pokemon(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = PokemonSprite(
                frontDefault = "front.png",
                backDefault = "back.png"
            ),
            stats = listOf(
                PokemonStat(45, Stat("hp", "url"))
            )
        )
        coEvery { pokeApiService.getPokemon(1) } returns pokemon

        // when
        val result = provider.getContent(pokemonId)

        // then
        assertEquals("Bulbasaur", result.title)
        assertEquals("포켓몬 #1 - Bulbasaur", result.description)
        assertEquals("front.png", result.imageUrl)
        assertEquals("POKEMON", result.contentType)
    }

    @Test
    fun `getContents는 추천 엔진의 ID로 컨텐츠를 조회한다`() = runBlocking {
        // given
        val count = 2
        val recommendedIds = listOf("1", "2")
        val pokemon1 = Pokemon(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = PokemonSprite("front1.png", "back1.png"),
            stats = emptyList()
        )
        val pokemon2 = Pokemon(
            id = 2,
            name = "ivysaur",
            height = 10,
            sprites = PokemonSprite("front2.png", "back2.png"),
            stats = emptyList()
        )

        coEvery { recommendationEngine.getRecommendedIds(any(), count) } returns recommendedIds
        coEvery { pokeApiService.getPokemon(1) } returns pokemon1
        coEvery { pokeApiService.getPokemon(2) } returns pokemon2

        // when
        val result = provider.getContents(count)

        // then
        assertEquals(2, result.size)
        assertEquals("Bulbasaur", result[0].title)
        assertEquals("Ivysaur", result[1].title)
    }

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
        coEvery { recommendationEngine.getRecommendedIds(any(), 3) } returns listOf("1", "1", "1")

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