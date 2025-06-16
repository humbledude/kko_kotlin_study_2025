package com.example.anika.pokemon.application.port.service

import com.example.anika.pokemon.application.port.outcoming.FavoritePort
import com.example.anika.pokemon.application.port.outcoming.PokemonPort
import com.example.anika.pokemon.domain.Pokemon
import com.example.anika.pokemon.domain.Stat
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class FavoriteServiceTest {
    @MockK
    lateinit var favoritePort: FavoritePort

    @MockK
    lateinit var pokemonPort: PokemonPort

    @InjectMockKs
    lateinit var favoriteService: FavoriteService

    private val pokemon =
        Pokemon(
            id = 1,
            name = "bulbasaur",
            height = 7,
            frontImageUrl = "front.png",
            backImageUrl = "back.png",
            stats = listOf(Stat("hp", 45)),
        )

    @Test
    fun `즐겨찾기 목록을 조회한다`() {
        every { favoritePort.getAllFavoritePokemonIds() } returns listOf(1)
        every { pokemonPort.getPokemonById(1) } returns pokemon

        val actual = favoriteService.getFavoriteList()

        assertEquals(listOf(pokemon), actual)
    }

    @Test
    fun `포켓몬을 즐겨찾기에 추가한다`() {
        every { favoritePort.addByPokemonId(1) } returns true

        val result = favoriteService.addFavorite(1)

        assertTrue(result)
    }
}
