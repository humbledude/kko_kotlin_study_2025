package com.example.anika.pokemon.application.port.service

import com.example.anika.pokemon.application.port.incoming.PokemonUseCase
import com.example.anika.pokemon.application.port.outcoming.PokemonPort
import com.example.anika.pokemon.domain.Pokemon
import org.springframework.stereotype.Service

@Service
class PokemonService(
    private val pokemonPort: PokemonPort,
) : PokemonUseCase {
    override fun getPokemon(id: Int): Pokemon {
        return pokemonPort.getPokemonById(id)
    }
}
