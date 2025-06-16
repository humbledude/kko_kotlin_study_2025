package com.example.anika.pokemon.application.port.incoming

import com.example.anika.pokemon.domain.Pokemon

interface PokemonUseCase {
    fun getPokemon(id: Int): Pokemon
}
