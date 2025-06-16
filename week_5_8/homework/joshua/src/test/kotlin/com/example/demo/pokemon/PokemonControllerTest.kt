package com.example.demo.pokemon

import com.example.demo.pokemon.model.MyPokemon
import com.example.demo.pokemon.model.MyPokemonStat
import com.example.demo.pokemon.model.PokemonNotFoundException
import com.example.demo.pokemon.model.PokemonSprite
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import kotlin.test.Test

@WebMvcTest(PokemonController::class)
@Import(PokeApiServiceMockConfig::class)
class PokemonControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var pokeApiService: PokeApiService

    @Autowired
    lateinit var favoriteService: FavoriteService

    @Test
    fun `정상 응답 케이스`() {
        coEvery { pokeApiService.getMyPokemon(1) } returns Fixtures.bulbasaur

        mockMvc.get("/pokemon/1")
            .andExpect {
                status { isOk()}
                content { contentType(MediaType.APPLICATION_JSON_VALUE)}

                jsonPath("$.name") { value("bulbasaur")}
                jsonPath("$.height") { value(7)}
            }
    }

    @Test
    fun `비정상 응답 케이스`() {
        coEvery { pokeApiService.getMyPokemon(1) } throws PokemonNotFoundException("test")

        mockMvc.get("/pokemon/1")
            .andExpect {
                status { isBadRequest()}
                content { contentType(MediaType.APPLICATION_JSON_VALUE)}

                jsonPath("$.message") { value("test")}
            }
    }

}

@TestConfiguration
class PokeApiServiceMockConfig {
    @Bean
    fun pokeApiService(): PokeApiService = mockk()

    @Bean
    fun favoriteService(): FavoriteService = mockk()
}

