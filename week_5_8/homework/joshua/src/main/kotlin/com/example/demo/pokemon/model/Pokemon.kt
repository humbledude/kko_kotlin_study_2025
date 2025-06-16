package com.example.demo.pokemon.model

data class Pokemon (
    val id: Int,
    val name: String,
    val height: Int,
    val sprites: PokemonSprite,
    val stats: List<PokemonStat>,
)