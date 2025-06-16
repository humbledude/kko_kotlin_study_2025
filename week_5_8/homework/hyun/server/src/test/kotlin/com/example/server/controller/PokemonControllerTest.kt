package com.example.server.controller

import com.example.server.dto.PokemonInfoDto
import com.example.server.service.PokemonService
import com.fasterxml.jackson.databind.ObjectMapper
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@WebMvcTest(PokemonController::class)
class PokemonControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var pokemonService: PokemonService

    val mapper = ObjectMapper()

    @Test
    fun apiTest() {
        // given
        val id = "1"
        val expected = PokemonInfoDto(
            name = "bulbasaur",
            height = 7,
            number = 1,
            frontImg = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
            backImg = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png",
            status = listOf()
        )

        // when
        `when`(pokemonService.getPocketMonsterInfoById(anyString()))
            .thenReturn(expected)

        // then
        mockMvc.perform(
            get("/pokemon/info")
                .queryParam("id", id)
        )
            .andExpect(status().isOk)
            .andExpect(content().string(mapper.writeValueAsString(expected)))
    }

    @Test
    fun hasNullId() {
        // given
        val id = null

        // then
        mockMvc.perform(
            get("/pokemon/info")
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().string("NPE"))
    }
}