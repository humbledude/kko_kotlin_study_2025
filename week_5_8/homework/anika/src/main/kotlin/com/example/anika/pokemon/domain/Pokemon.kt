package com.example.anika.pokemon.domain

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val frontImageUrl: String,
    val backImageUrl: String,
    val stats: List<Stat>,
)
