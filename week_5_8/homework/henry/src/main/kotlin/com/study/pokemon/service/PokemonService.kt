package com.study.pokemon.service

import com.study.pokemon.domain.PokemonRepository
import com.study.pokemon.domain.model.Pokemon
import org.springframework.stereotype.Service

@Service
class PokemonService(
    private val pokemonRepository: PokemonRepository,
) {
    suspend fun getPokemonInfo(pokemonId: Int): Pokemon = pokemonRepository.getPokemonInfo(pokemonId)
}
