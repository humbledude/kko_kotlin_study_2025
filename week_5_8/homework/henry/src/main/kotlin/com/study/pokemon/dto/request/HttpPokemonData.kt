package com.study.pokemon.dto.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.study.pokemon.domain.model.Pokemon

data class HttpPokemonData(
    val name: String,
    val height: Int,
    val id: Int,
    val sprites: SpritesData,
    val stats: List<StatData>,
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class SpritesData(
        val frontDefault: String,
        val backDefault: String,
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class StatData(
        val baseStat: Int,
        val stat: StatInfoData,
    ) {
        data class StatInfoData(
            val name: String,
            val url: String,
        )
    }

    fun toDomain(): Pokemon =
        Pokemon(
            name = name,
            height = height,
            id = id,
            sprites =
                Pokemon.Sprites(
                    frontDefault = sprites.frontDefault,
                    backDefault = sprites.backDefault,
                ),
            stats =
                stats.map {
                    Pokemon.Stat(
                        baseStat = it.baseStat,
                        stat =
                            Pokemon.Stat.StatInfo(
                                name = it.stat.name,
                                url = it.stat.url,
                            ),
                    )
                },
        )
}
