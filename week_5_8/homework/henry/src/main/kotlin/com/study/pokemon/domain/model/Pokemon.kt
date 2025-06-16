package com.study.pokemon.domain.model

data class Pokemon(
    val name: String,
    val height: Int,
    val id: Int,
    val sprites: Sprites,
    val stats: List<Stat>,
) {
    data class Sprites(
        val frontDefault: String,
        val backDefault: String,
    )

    data class Stat(
        val baseStat: Int,
        val stat: StatInfo,
    ) {
        data class StatInfo(
            val name: String,
            val url: String,
        )
    }
}
