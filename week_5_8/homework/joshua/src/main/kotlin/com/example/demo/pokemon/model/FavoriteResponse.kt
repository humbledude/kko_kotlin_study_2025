package com.example.demo.pokemon.model

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("total", "pokemons")
data class FavoriteResponse (
    val pokemons: List<MyPokemon>
) {
    val total : Int
        get() = pokemons.size
}