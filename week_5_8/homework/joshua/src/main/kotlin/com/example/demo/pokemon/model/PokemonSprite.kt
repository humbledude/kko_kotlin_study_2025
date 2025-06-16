package com.example.demo.pokemon.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PokemonSprite (
    val frontDefault: String,
    val backDefault: String,
)
