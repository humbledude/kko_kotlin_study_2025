package com.example.anika.pokemon.application.port.service

import com.example.anika.pokemon.application.port.outcoming.PokemonPort
import com.example.anika.pokemon.domain.Pokemon
import com.example.anika.pokemon.domain.Stat
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class PokemonServiceTest {
    @MockK
    lateinit var pokemonPort: PokemonPort

    @InjectMockKs
    lateinit var pokemonService: PokemonService

    private val expected =
        Pokemon(
            id = 1,
            name = "bulbasaur",
            height = 7,
            frontImageUrl = "front.png",
            backImageUrl = "back.png",
            stats = listOf(Stat("hp", 45)),
        )

    @Test
    fun `id로 포켓몬을 조회한다`() {
        every { pokemonPort.getPokemonById(1) } returns expected

        val actual = pokemonService.getPokemon(1)

        assertEquals(expected, actual)
    }
}
