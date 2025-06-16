package com.example.anika.pokemon.adapter.incoming.web

import com.example.anika.pokemon.domain.Pokemon

data class GetPokemonResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val frontImageUrl: String,
    val backImageUrl: String,
    val stats: List<Stat>,
) {
    data class Stat(
        val name: String,
        val baseStat: Int,
    )

    companion object {
        fun of(pokemon: Pokemon): GetPokemonResponse =
            GetPokemonResponse(
                id = pokemon.id,
                name = pokemon.name,
                height = pokemon.height,
                frontImageUrl = pokemon.frontImageUrl,
                backImageUrl = pokemon.backImageUrl,
                stats =
                    pokemon.stats.map {
                        Stat(name = it.name, baseStat = it.baseStat)
                    },
            )
    }
}