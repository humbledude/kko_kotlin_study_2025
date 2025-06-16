package com.example.server.dto

data class PokemonInfo (
    val name: String,
    val height: Int,
    val number: Int,
    val frontImg: String,
    val backImg: String,
    val status: List<PokemonStatus>
)

data class PokemonStatus(
    val statName: String,
    val baseStat: Int
)