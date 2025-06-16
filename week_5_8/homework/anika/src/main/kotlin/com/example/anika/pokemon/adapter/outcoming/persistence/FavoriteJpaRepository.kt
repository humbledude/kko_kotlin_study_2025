package com.example.anika.pokemon.adapter.outcoming.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface FavoriteJpaRepository : JpaRepository<FavoriteEntity, Long> {
    fun existsByPokemonId(pokemonId: Int): Boolean
    fun deleteByPokemonId(pokemonId: Int): Long
}