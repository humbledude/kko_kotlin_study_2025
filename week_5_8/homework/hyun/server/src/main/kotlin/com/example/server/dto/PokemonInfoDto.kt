package com.example.server.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PokemonInfoDto(
    val name: String,
    val height: Int,
    val number: Int,
    val frontImg: String,
    val backImg: String,
    val status: List<PokemonStatusDto>
) {
    companion object {
        fun valueOf(info: PokemonInfo): PokemonInfoDto {
            return PokemonInfoDto(
                name = info.name,
                height = info.height,
                number = info.number,
                frontImg = info.frontImg,
                backImg = info.backImg,
                status = info.status.map { it -> PokemonStatusDto(it.statName, it.baseStat) }
            )
        }
    }
}

data class PokemonStatusDto(
    @JsonProperty(value = "stat_name") val statName: String,
    @JsonProperty(value = "base_stat") val baseStat: Int
)