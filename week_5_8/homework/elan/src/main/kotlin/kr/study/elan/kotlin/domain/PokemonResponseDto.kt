package kr.study.elan.kotlin.domain

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PokemonResponseDto(
    val id: Long = 0L,
    val pokemonId: Int,
    val name: String,
    val height: Int,
    val frontSprite: String,
    val backendSprite: String,
    val stats: Collection<PokemonStat> = emptyList()
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class PokemonStat(
        val name: String,
        val baseStat: Int,
    )
}

