package com.example.anika.pokemon.application.port.incoming

import com.example.anika.pokemon.domain.Pokemon

interface FavoriteUseCase {
    fun addFavorite(pokemonId: Int): Boolean

    fun deleteFavorite(pokemonId: Int): Boolean

    fun getFavoriteList(): List<Pokemon>

    fun isFavorite(pokemonId: Int): Boolean
}
