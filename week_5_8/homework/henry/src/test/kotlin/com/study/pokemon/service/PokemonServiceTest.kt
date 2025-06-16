package com.study.pokemon.service

import com.study.pokemon.domain.PokemonRepository
import com.study.pokemon.domain.model.Pokemon
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class PokemonServiceTest {
    private val pokemonRepository = mockk<PokemonRepository>()
    private val pokemonService = PokemonService(pokemonRepository)

    @Test
    fun `포켓몬 정보를 가져온다`() {
        // given
        val pokemonId = 1
        val pokemon =
            Pokemon(
                id = pokemonId,
                name = "test",
                height = 1,
                sprites =
                    Pokemon.Sprites(
                        frontDefault = "front_default",
                        backDefault = "back_default",
                    ),
                stats =
                    listOf(
                        Pokemon.Stat(
                            baseStat = 45,
                            stat =
                                Pokemon.Stat.StatInfo(
                                    name = "hp",
                                    url = "https://test.co/1",
                                ),
                        ),
                        Pokemon.Stat(
                            baseStat = 49,
                            stat =
                                Pokemon.Stat.StatInfo(
                                    name = "attack",
                                    url = "https://test.co/2",
                                ),
                        ),
                    ),
            )

        coEvery { pokemonRepository.getPokemonInfo(pokemonId) } returns pokemon

        // when
        runBlocking {
            val pokemonActual = pokemonService.getPokemonInfo(pokemonId)

            assertThat(pokemonActual).isNotNull
            assertThat(pokemonActual.id).isEqualTo(pokemonId)
            assertThat(pokemonActual.name).isEqualTo("test")
            assertThat(pokemonActual.height).isEqualTo(1)
        }
    }
}
