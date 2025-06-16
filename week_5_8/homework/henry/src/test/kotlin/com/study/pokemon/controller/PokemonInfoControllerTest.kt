package com.study.pokemon.controller

import com.ninjasquad.springmockk.MockkBean
import com.study.pokemon.dto.request.HttpPokemonData
import com.study.pokemon.exception.CustomException
import com.study.pokemon.service.PokemonService
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(PokemonInfoController::class)
class PokemonInfoControllerTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var pokemonService: PokemonService

    @Test
    fun `포켓몬 정보가 정상적으로 응답하는지`() {
        // Given
        val pokemonId = 1
        val pokemon =
            HttpPokemonData(
                id = pokemonId,
                name = "test",
                height = 1,
                sprites =
                    HttpPokemonData.SpritesData(
                        frontDefault = "front_default",
                        backDefault = "back_default",
                    ),
                stats =
                    listOf(
                        HttpPokemonData.StatData(
                            baseStat = 45,
                            stat =
                                HttpPokemonData.StatData.StatInfoData(
                                    name = "hp",
                                    url = "https://test.co/1",
                                ),
                        ),
                        HttpPokemonData.StatData(
                            baseStat = 49,
                            stat =
                                HttpPokemonData.StatData.StatInfoData(
                                    name = "attack",
                                    url = "https://test.co/2",
                                ),
                        ),
                    ),
            )

        coEvery { pokemonService.getPokemonInfo(pokemonId) } returns pokemon.toDomain()

        // When & Then
        webTestClient
            .get()
            .uri("/pokemon/$pokemonId")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(pokemonId)
            .jsonPath("$.name").isEqualTo(pokemon.name)
            .jsonPath("$.height").isEqualTo(pokemon.height)
            .jsonPath("$.sprites.frontDefault").isEqualTo(pokemon.sprites.frontDefault)
            .jsonPath("$.sprites.backDefault").isEqualTo(pokemon.sprites.backDefault)
            .jsonPath("$.stats").isArray
    }

    @Test
    fun `포켓몬 정보가 없을 때 에러가 발생하는지`() {
        // Given
        val pokemonId = -1

        coEvery { pokemonService.getPokemonInfo(pokemonId) } throws
            CustomException(
                message = "포켓몬 못 찾음",
                status = HttpStatus.NOT_FOUND,
            )

        // When & Then
        webTestClient
            .get()
            .uri("/pokemon/$pokemonId")
            .exchange()
            .expectStatus().isNotFound
    }
}
