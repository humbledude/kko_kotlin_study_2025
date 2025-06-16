package com.example.anika.pokemon.application.port.outcoming

interface FavoritePort {
    fun addByPokemonId(pokemonId: Int): Boolean

    fun deleteByPokemonId(pokemonId: Int): Boolean

    fun getAllFavoritePokemonIds(): List<Int>

    fun existsByPokemonId(pokemonId: Int): Boolean
}
