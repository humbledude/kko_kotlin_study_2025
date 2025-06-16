package com.example.demo.pokemon

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull
import kotlin.test.Test
import kotlin.test.assertEquals

class FavoriteServiceTest {

    val pokeApiService : PokeApiService = mockk()
    val myPokemonRepository: MyPokemonRepository = mockk()
    val favoriteService = FavoriteService(pokeApiService, myPokemonRepository)

    @Test
    fun `저장 하나 해보기`() {
        coEvery { pokeApiService.getMyPokemon(1) } returns Fixtures.bulbasaur
        every { myPokemonRepository.save(Fixtures.bulbasaur)} returns Fixtures.bulbasaur
        every { myPokemonRepository.findByIdOrNull(1) } returns null

        val (pokemon, saved) = favoriteService.saveFavoritePokemon(1)

        assertEquals(Fixtures.bulbasaur, pokemon)
        assertEquals(true, saved)
    }

    @Test
    fun `이미 저장된거 다시 저장하기`() {
        coEvery { pokeApiService.getMyPokemon(1) } returns Fixtures.bulbasaur
        every { myPokemonRepository.save(Fixtures.bulbasaur)} returns Fixtures.bulbasaur
        every { myPokemonRepository.findByIdOrNull(1) } returns Fixtures.bulbasaur

        val (pokemon, saved) = favoriteService.saveFavoritePokemon(1)

        assertEquals(Fixtures.bulbasaur, pokemon)
        assertEquals(false, saved)
    }

}