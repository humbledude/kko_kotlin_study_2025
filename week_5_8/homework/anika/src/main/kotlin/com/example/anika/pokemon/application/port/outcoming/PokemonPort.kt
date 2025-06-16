package com.example.anika.pokemon.application.port.outcoming

import com.example.anika.pokemon.domain.Pokemon

interface PokemonPort {
    fun getPokemonById(id: Int): Pokemon
}
