package com.study.pokemon.domain

import com.study.pokemon.domain.model.Pokemon

fun interface PokemonRepository {
    suspend fun getPokemonInfo(pokemonId: Int): Pokemon
}
