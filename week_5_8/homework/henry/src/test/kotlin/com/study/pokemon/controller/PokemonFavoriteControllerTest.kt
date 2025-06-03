package com.study.pokemon.controller

import com.ninjasquad.springmockk.MockkBean
import com.study.pokemon.domain.model.PokemonFavorite
import com.study.pokemon.exception.CustomException
import com.study.pokemon.service.PokemonFavoriteService
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDateTime

@WebFluxTest(PokemonFavoriteController::class)
class PokemonFavoriteControllerTest {
    
    @Autowired
    private lateinit var webTestClient: WebTestClient
    
    @MockkBean
    private lateinit var pokemonFavoriteService: PokemonFavoriteService
    
    @Test
    fun `포켓몬 ID로 즐겨찾기 추가 요청 시 정상 응답한다`() {
        // given
        val pokemonId = 1
        val favorite = PokemonFavorite(
            id = 1,
            pokemonId = pokemonId,
            pokemonName = "pikachu",
            pokemonImage = "http://example.com/pikachu.png",
            createdAt = LocalDateTime.now()
        )
        
        coEvery { pokemonFavoriteService.addFavorite(pokemonId) } returns favorite
        
        // when & then
        webTestClient.post()
            .uri("/pokemon/favorite/{id}", pokemonId)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.pokemonId").isEqualTo(favorite.pokemonId)
            .jsonPath("$.pokemonName").isEqualTo(favorite.pokemonName)
            .jsonPath("$.pokemonImage").isEqualTo(favorite.pokemonImage)
    }
    
    @Test
    fun `즐겨찾기 목록 조회 요청 시 정상 응답한다`() {
        // given
        val favorites = listOf(
            PokemonFavorite(
                id = 1,
                pokemonId = 1,
                pokemonName = "pikachu",
                pokemonImage = "http://example.com/pikachu.png",
                createdAt = LocalDateTime.now()
            ),
            PokemonFavorite(
                id = 2,
                pokemonId = 2,
                pokemonName = "bulbasaur",
                pokemonImage = "http://example.com/bulbasaur.png",
                createdAt = LocalDateTime.now()
            )
        )
        
        coEvery { pokemonFavoriteService.getFavorites() } returns favorites
        
        // when & then
        webTestClient.get()
            .uri("/pokemon/favorite")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].pokemonId").isEqualTo(favorites[0].pokemonId)
            .jsonPath("$[0].pokemonName").isEqualTo(favorites[0].pokemonName)
            .jsonPath("$[1].pokemonId").isEqualTo(favorites[1].pokemonId)
            .jsonPath("$[1].pokemonName").isEqualTo(favorites[1].pokemonName)
    }
    
    @Test
    fun `잘못된 포켓몬 ID로 요청 시 에러 응답한다`() {
        // given
        val invalidPokemonId = -1
        coEvery { pokemonFavoriteService.addFavorite(invalidPokemonId) } throws 
            CustomException(message = "", status = HttpStatus.BAD_REQUEST)
        
        // when & then
        webTestClient.post()
            .uri("/pokemon/favorite/{id}", invalidPokemonId)
            .exchange()
            .expectStatus().isBadRequest
    }
} 