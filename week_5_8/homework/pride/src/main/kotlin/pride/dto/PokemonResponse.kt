package pride.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PokemonResponse(
    val name: String,
    val height: Int,
    val id: Int,
    val sprites: Sprites,
    val stats: List<Stat>
) {
    data class Sprites(
        @JsonProperty("front_default")
        val frontDefault: String,
        @JsonProperty("back_default")
        val backDefault: String
    )

    data class Stat(
        val stat: StatInfo,
        @JsonProperty("base_stat")
        val baseStat: Int
    )

    data class StatInfo(
        val name: String
    )
} 