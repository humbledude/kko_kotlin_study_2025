package kr.study.elan.kotlin.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class PokemonApiDto(
    val id: Int,
    val name: String,
    val height: Int,
    @JsonProperty("sprites") val sprite: Sprite,
    val stats: Collection<Stat>,
) {
    data class Sprite(
        @JsonProperty("front_default") val frontDefault: String,
        @JsonProperty("back_default") val backDefault: String,
    )

    data class Stat(
        @JsonProperty("base_stat") val baseStat: Int,
        val stat: StatItem,
    ) {
        data class StatItem(
            val name: String,
        )
    }
}





