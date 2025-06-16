package com.example.anika.pokemon.adapter.outcoming.web

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class PokeApiAdapterTest {
    @MockK
    lateinit var webClient: WebClient

    @MockK
    lateinit var uriSpec: WebClient.RequestHeadersUriSpec<*>

    @MockK
    lateinit var responseSpec: WebClient.ResponseSpec

    @MockK
    lateinit var mono: Mono<PokeApiResponse>

    @InjectMockKs
    lateinit var adapter: PokeApiAdapter

    private val expected =
        PokeApiResponse(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = PokeApiResponse.Sprites("front.png", "back.png"),
            stats = listOf(PokeApiResponse.Stat(45, PokeApiResponse.Stat.StatName("hp"))),
        )

    @BeforeEach
    fun setUp() {
        every { webClient.get() } returns uriSpec
        every { uriSpec.uri(any(), expected.id) } returns uriSpec
        every { uriSpec.retrieve() } returns responseSpec
        every { responseSpec.bodyToMono(PokeApiResponse::class.java) } returns mono
        every { mono.block() } returns expected
    }

    @Test
    fun `id로 포켓몬을 조회한다`() {
        val actual = adapter.getPokemonById(expected.id)

        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.height, actual.height)
        assertEquals(expected.sprites.frontDefault, actual.frontImageUrl)
        assertEquals(expected.sprites.backDefault, actual.backImageUrl)
        assertEquals(expected.stats.first().baseStat, actual.stats.first().baseStat)
        assertEquals(expected.stats.first().stat.name, actual.stats.first().name)
    }

    @Test
    fun `예외 발생할 경우 IllegalStateException를 던진다`() {
        every { responseSpec.bodyToMono(PokeApiResponse::class.java) } throws RuntimeException()

        assertThrows<IllegalStateException> { adapter.getPokemonById(expected.id) }
    }
}
