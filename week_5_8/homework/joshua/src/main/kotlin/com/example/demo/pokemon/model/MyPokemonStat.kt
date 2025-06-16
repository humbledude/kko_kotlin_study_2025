package com.example.demo.pokemon.model

data class MyPokemonStat(
    val name: String,
    val baseStat: Int,
) {
    companion object {
        fun fromPokemonStat(stat: PokemonStat) = MyPokemonStat(
            name = stat.stat.name,
            baseStat = stat.baseStat
        )
    }
}
