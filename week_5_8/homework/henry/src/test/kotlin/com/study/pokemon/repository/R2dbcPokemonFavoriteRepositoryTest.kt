package com.study.pokemon.repository

import com.study.pokemon.domain.model.PokemonFavorite
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataR2dbcTest
@Import(R2dbcPokemonFavoriteRepository::class)
@ActiveProfiles("test")
class R2dbcPokemonFavoriteRepositoryTest(
    @Autowired val r2dbcPokemonFavoriteRepository: R2dbcPokemonFavoriteRepository
) {
    @Autowired
    private lateinit var coroutineRepository: CoroutinePokemonFavoriteRepository

    @BeforeEach
    fun setUp() {
        // 테이블 초기화
        runBlocking {
            coroutineRepository.deleteAll()
        }
    }
    
    @Test
    fun `새로운 포켓몬 즐겨찾기를 저장한다`(): Unit = runBlocking {
        // given
        val favorite = PokemonFavorite(
            pokemonId = 1,
            pokemonName = "pikachu",
            pokemonImage = "http://example.com/pikachu.png"
        )
        
        // when
        val savedFavorite = r2dbcPokemonFavoriteRepository.save(favorite)
        
        // then
        assertThat(savedFavorite).isNotNull
        assertThat(savedFavorite.pokemonId).isEqualTo(savedFavorite.pokemonId)
        assertThat(savedFavorite.pokemonName).isEqualTo(favorite.pokemonName)
        assertThat(savedFavorite.pokemonImage).isEqualTo(favorite.pokemonImage)
    }
    
    @Test
    fun `포켓몬 ID로 즐겨찾기를 조회한다`(): Unit = runBlocking {
        // given
        val favorite = PokemonFavorite(
            pokemonId = 2,
            pokemonName = "bulbasaur",
            pokemonImage = "http://example.com/bulbasaur.png"
        )
        coroutineRepository.save(favorite)
        
        // when
        val foundFavorite = r2dbcPokemonFavoriteRepository.findByPokemonId(2)
        
        // then
        assertThat(foundFavorite).isNotNull
        assertThat(foundFavorite?.pokemonId).isEqualTo(favorite.pokemonId)
        assertThat(foundFavorite?.pokemonName).isEqualTo(favorite.pokemonName)
        assertThat(foundFavorite?.pokemonImage).isEqualTo(favorite.pokemonImage)
    }
    
    @Test
    fun `존재하지 않는 포켓몬 ID로 조회하면 null을 반환한다`() = runBlocking {
        // when
        val foundFavorite = r2dbcPokemonFavoriteRepository.findByPokemonId(999)
        
        // then
        assertThat(foundFavorite).isNull()
    }
    
    @Test
    fun `모든 즐겨찾기 목록을 조회한다`(): Unit = runBlocking {
        // given
        val favorite1 = PokemonFavorite(
            pokemonId = 1,
            pokemonName = "pikachu",
            pokemonImage = "http://example.com/pikachu.png"
        )
        val favorite2 = PokemonFavorite(
            pokemonId = 2,
            pokemonName = "bulbasaur",
            pokemonImage = "http://example.com/bulbasaur.png"
        )
        
        coroutineRepository.save(favorite1)
        coroutineRepository.save(favorite2)
        
        // when
        val favorites = r2dbcPokemonFavoriteRepository.findAll()
        
        // then
        assertThat(favorites).isNotEmpty
        assertThat(favorites).hasSize(2)
        assertThat(favorites.map { it.pokemonId }).containsExactlyInAnyOrder(1, 2)
    }
} 