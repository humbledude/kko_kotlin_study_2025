package com.study.pokemon.domain

import com.study.pokemon.domain.model.PokemonFavorite

interface PokemonFavoriteRepository {
    suspend fun save(pokemonFavorite: PokemonFavorite): PokemonFavorite
    suspend fun findAll(): List<PokemonFavorite>
    suspend fun findByPokemonId(pokemonId: Int): PokemonFavorite?
} 