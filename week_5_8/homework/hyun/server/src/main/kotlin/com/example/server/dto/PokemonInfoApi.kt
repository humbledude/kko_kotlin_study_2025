package com.example.server.dto

import com.example.server.dto.PokemonStat.Companion.toStatus
import com.fasterxml.jackson.annotation.JsonProperty

data class PokemonInfoApi(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonTypeSlot>,
    val stats: List<PokemonStat>,
    val sprites: PokemonSprites,
) {
    companion object {
        fun toEntity(api: PokemonInfoApi): PokemonInfo {
            return PokemonInfo(
                name = api.name,
                height = api.height,
                number = api.id,
                frontImg = api.sprites.frontDefault ?: "",
                backImg = api.sprites.backDefault ?: "",
                status = api.stats.map { it -> toStatus(it) }.toList()
            )
        }
    }
}

data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonType
)

data class PokemonType(
    val name: String,
    val url: String
)

data class PokemonStat(
    @JsonProperty("base_stat") val baseStat: Int,
    val effort: Int,
    val stat: StatDetail
) {
    companion object {
        fun toStatus(stat: PokemonStat): PokemonStatus {
            return PokemonStatus(
                statName = stat.stat.name,
                baseStat = stat.baseStat
            )
        }
    }
}

data class StatDetail(
    val name: String,
    val url: String
)

data class PokemonSprites(
    @JsonProperty("front_default") val frontDefault: String?,
    @JsonProperty("back_default") val backDefault: String?
)
