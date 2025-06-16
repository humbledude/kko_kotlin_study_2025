package com.example.demo.pokemon.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PokemonStat(
    val baseStat: Int,
    val stat: Stat
)
