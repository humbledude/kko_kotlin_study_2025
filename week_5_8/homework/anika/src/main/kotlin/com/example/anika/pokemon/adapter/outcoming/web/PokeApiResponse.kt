package com.example.anika.pokemon.adapter.outcoming.web

import com.example.anika.pokemon.domain.Pokemon
import com.example.anika.pokemon.domain.Stat
import com.fasterxml.jackson.annotation.JsonProperty

data class PokeApiResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val sprites: Sprites,
    val stats: List<Stat>,
) {
    data class Sprites(
        @JsonProperty("front_default")
        val frontDefault: String,
        @JsonProperty("back_default")
        val backDefault: String,
    )

    data class Stat(
        @JsonProperty("base_stat")
        val baseStat: Int,
        val stat: StatName,
    ) {
        data class StatName(
            val name: String,
        )
    }

    fun toPokemon(): Pokemon =
        Pokemon(
            id = id,
            name = name,
            height = height,
            frontImageUrl = sprites.frontDefault,
            backImageUrl = sprites.backDefault,
            stats = stats.map { Stat(it.stat.name, it.baseStat) },
        )
}
