package com.example.server.repository

import com.example.server.dto.PokemonInfoDb
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test
import kotlin.test.assertEquals


@DataJpaTest
class PokemonJpaRepositoryTest @Autowired constructor(
    val entityManager: EntityManager,
    val pokemonRepository: PokemonJpaRepository
) {

    @Test
    fun saveFavorite() {
        // given
        val pokemonDb = PokemonInfoDb(name = "TEST", number = 12345, frontImg = "frontImg", backImg = "backImg")

        // when
        val saved = pokemonRepository.save(pokemonDb)

        // then
        assertEquals(saved.number, 12345)
        assertEquals(saved.name, "TEST")
        assertEquals(saved.frontImg, "frontImg")
        assertEquals(saved.backImg, "backImg")
    }

    @Test
    fun getFavoritePokemonList() {
        // given
        entityManager.persist(PokemonInfoDb(name = "TEST-1", number = 1, frontImg = "frontImg", backImg = "backImg"))
        entityManager.persist(PokemonInfoDb(name = "TEST-2", number = 2, frontImg = "frontImg", backImg = "backImg"))
        entityManager.persist(PokemonInfoDb(name = "TEST-3", number = 3, frontImg = "frontImg", backImg = "backImg"))
        entityManager.flush()

        // when
        val favoriteList = pokemonRepository.findAll()

        // then
        assertEquals(3, favoriteList.size)
    }
}