package com.study.pokemon.service

import com.study.pokemon.domain.PokemonFavoriteRepository
import com.study.pokemon.domain.model.Pokemon
import com.study.pokemon.domain.model.PokemonFavorite
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PokemonFavoriteServiceTest {

    private lateinit var pokemonFavoriteRepository: PokemonFavoriteRepository
    private lateinit var pokemonService: PokemonService
    private lateinit var pokemonFavoriteService: PokemonFavoriteService

    @BeforeEach
    fun setUp() {
        pokemonFavoriteRepository = mockk()
        pokemonService = mockk()
        pokemonFavoriteService = PokemonFavoriteService(pokemonFavoriteRepository, pokemonService)
    }

    @Test
    fun `즐겨찾기 추가 시 이미 등록된 포켓몬이면 기존 데이터를 반환한다`() = runBlocking {
        // given
        val pokemonId = 1
        val existingFavorite = PokemonFavorite(
            id = 1,
            pokemonId = pokemonId,
            pokemonName = "pikachu",
            pokemonImage = "http://example.com/pikachu.png"
        )
        
        coEvery { pokemonFavoriteRepository.findByPokemonId(pokemonId) } returns existingFavorite
        
        // when
        val result = pokemonFavoriteService.addFavorite(pokemonId)
        
        // then
        assertThat(result).isEqualTo(existingFavorite)
        coVerify(exactly = 1) { pokemonFavoriteRepository.findByPokemonId(pokemonId) }
        coVerify(exactly = 0) { pokemonService.getPokemonInfo(any()) }
        coVerify(exactly = 0) { pokemonFavoriteRepository.save(any()) }
    }
    
    @Test
    fun `즐겨찾기 추가 시 새로운 포켓몬이면 저장 후 반환한다`() = runBlocking {
        // given
        val pokemonId = 1
        val pokemonName = "test"
        val pokemonImage = "http://example.com/pikachu.png"

        val pokemon =
            Pokemon(
                id = pokemonId,
                name = pokemonName,
                height = 1,
                sprites =
                Pokemon.Sprites(
                    frontDefault = pokemonImage,
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
        
        val newFavorite = PokemonFavorite(
            id = 1,
            pokemonId = pokemonId,
            pokemonName = pokemonName,
            pokemonImage = pokemonImage
        )
        
        coEvery { pokemonFavoriteRepository.findByPokemonId(pokemonId) } returns null
        coEvery { pokemonService.getPokemonInfo(pokemonId) } returns pokemon
        coEvery { pokemonFavoriteRepository.save(any()) } returns newFavorite
        
        // when
        val result = pokemonFavoriteService.addFavorite(pokemonId)
        
        // then
        assertThat(result).isEqualTo(newFavorite)
        coVerify(exactly = 1) { pokemonFavoriteRepository.findByPokemonId(pokemonId) }
        coVerify(exactly = 1) { pokemonService.getPokemonInfo(pokemonId) }
        coVerify(exactly = 1) { pokemonFavoriteRepository.save(any()) }
    }
    
    @Test
    fun `모든 즐겨찾기 목록을 조회한다`() = runBlocking {
        // given
        val favorites = listOf(
            PokemonFavorite(
                id = 1,
                pokemonId = 1,
                pokemonName = "pikachu",
                pokemonImage = "http://example.com/pikachu.png"
            ),
            PokemonFavorite(
                id = 2,
                pokemonId = 2,
                pokemonName = "bulbasaur",
                pokemonImage = "http://example.com/bulbasaur.png"
            )
        )
        
        coEvery { pokemonFavoriteRepository.findAll() } returns favorites
        
        // when
        val result = pokemonFavoriteService.getFavorites()
        
        // then
        assertThat(result).isEqualTo(favorites)
        coVerify(exactly = 1) { pokemonFavoriteRepository.findAll() }
    }
} 