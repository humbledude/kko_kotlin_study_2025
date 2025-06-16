package com.example.anika.pokemon.adapter.incoming.web

import com.example.anika.pokemon.application.port.incoming.PokemonUseCase
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

@SpringBootTest
@AutoConfigureMockMvc
@Import(PokemonControllerTest.PokemonControllerTestConfig::class)
class PokemonControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var pokemonUseCase: PokemonUseCase

    @Test
    fun `정상 요청인 경우 포켓몬 정보를 반환한다`() {
        // given
        val expected =
            Pokemon(
                id = 1,
                name = "bulbasaur",
                height = 7,
                frontImageUrl = "front.png",
                backImageUrl = "back.png",
                stats = listOf(Stat("hp", 45)),
            )
        every { pokemonUseCase.getPokemon(expected.id) } returns expected

        // when
        val result = mockMvc.get("/pokemon/${expected.id}")

        // then
        result.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(expected.id) }
            jsonPath("$.name") { value(expected.name) }
            jsonPath("$.height") { value(expected.height) }
            jsonPath("$.frontImageUrl") { value(expected.frontImageUrl) }
            jsonPath("$.backImageUrl") { value(expected.backImageUrl) }
            jsonPath("$.stats[0].baseStat") { value(expected.stats.first().baseStat) }
            jsonPath("$.stats[0].name") { value(expected.stats.first().name) }
        }
    }

    @Test
    fun `유효하지 않은 id 요청인 경우 500 에러를 반환한다`() {
        // given
        val id = -1
        every { pokemonUseCase.getPokemon(id) } throws IllegalStateException()

        // when
        val result = mockMvc.get("/pokemon/$id")

        // then
        result.andExpect {
            status { isInternalServerError() }
        }
    }

    @TestConfiguration
    class PokemonControllerTestConfig {
        @Bean
        fun pokemonUseCase(): PokemonUseCase = mockk()
    }
}
