package com.example.anika.pokemon.adapter.incoming.web

import com.example.anika.pokemon.application.port.incoming.FavoriteUseCase
import com.example.anika.pokemon.domain.Pokemon
import com.example.anika.pokemon.domain.Stat
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@Import(FavoriteControllerTest.FavoriteControllerTestConfig::class)
class FavoriteControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var favoriteUseCase: FavoriteUseCase

    @Test
    fun `즐겨찾기 목록을 반환한다`() {
        // given
        val favoritePokemons =
            listOf(
                Pokemon(
                    id = 1,
                    name = "bulbasaur",
                    height = 7,
                    frontImageUrl = "front.png",
                    backImageUrl = "back.png",
                    stats = listOf(Stat("hp", 45)),
                ),
            )
        every { favoriteUseCase.getFavoriteList() } returns favoritePokemons

        // when
        val result = mockMvc.get("/favorites")

        // then
        result.andExpect {
            status { isOk() }
            jsonPath("$.length()") { value(favoritePokemons.size) }
            jsonPath("$[0].id") { value(favoritePokemons[0].id) }
            jsonPath("$[0].name") { value(favoritePokemons[0].name) }
        }
    }

    @Test
    fun `즐겨찾기 추가에 성공하면 목록을 반환한다`() {
        // given
        val pokemonId = 1
        every { favoriteUseCase.addFavorite(pokemonId) } returns true
        val favoritePokemons =
            listOf(
                Pokemon(
                    id = 1,
                    name = "bulbasaur",
                    height = 7,
                    frontImageUrl = "front.png",
                    backImageUrl = "back.png",
                    stats = listOf(Stat("hp", 45)),
                ),
            )
        every { favoriteUseCase.getFavoriteList() } returns favoritePokemons

        // when
        val result = mockMvc.post("/favorites/$pokemonId")

        // then
        result.andExpect {
            status { isOk() }
            jsonPath("$.length()") { value(1) }
            jsonPath("$[0].id") { value(pokemonId) }
        }
    }

    @Test
    fun `즐겨찾기 추가 실패 시 400 반환`() {
        // given
        val pokemonId = 100
        every { favoriteUseCase.addFavorite(pokemonId) } returns false

        // when
        val result = mockMvc.post("/favorites/$pokemonId")

        // then
        result.andExpect {
            status { isBadRequest() }
        }
    }

    @TestConfiguration
    class FavoriteControllerTestConfig {
        @Bean
        fun favoriteUseCase(): FavoriteUseCase = mockk()
    }
}
