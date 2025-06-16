package com.example.anika.pokemon.adapter.outcoming.persistence

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class FavoriteJpaRepositoryTest @Autowired constructor(
    val favoriteJpaRepository: FavoriteJpaRepository
) {
    @Test
    fun `저장`() {
        val entity = FavoriteEntity(pokemonId = 1)

        val saved = favoriteJpaRepository.save(entity)

        assertNotNull(saved.id)
        assertEquals(entity.pokemonId, saved.pokemonId)
    }

    @Test
    fun `전체 조회`() {
        val entityList = listOf(FavoriteEntity(pokemonId = 1), FavoriteEntity(pokemonId = 2), FavoriteEntity(pokemonId = 3))
        favoriteJpaRepository.saveAll(entityList)

        val all = favoriteJpaRepository.findAll()
        assertEquals(entityList.size, all.size)
        assertEquals(entityList.first().pokemonId, all.first().pokemonId)
        assertEquals(entityList.last().pokemonId, all.last().pokemonId)
    }
}