package com.joshua.feed.pokemon.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PokemonStat(
    val baseStat: Int,
    val stat: Stat
)

data class Stat(
    val name: String,
    val url: String
) 