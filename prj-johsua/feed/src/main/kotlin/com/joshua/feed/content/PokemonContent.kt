package com.joshua.feed.content

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.joshua.feed.domain.content.Content
import com.joshua.feed.pokemon.model.Pokemon
import java.time.LocalDateTime

class PokemonContent(
    private val pokemon: Pokemon
) {
    val content: Content
        get() {
            val body = ObjectMapper().createObjectNode().apply {
                put("id", pokemon.id)
                put("name", pokemon.name)
                put("height", pokemon.height)
                putArray("stats").apply {
                    pokemon.stats.forEach { stat ->
                        addObject().apply {
                            put("baseStat", stat.baseStat)
                            put("name", stat.stat.name)
                        }
                    }
                }
                putObject("sprites").apply {
                    put("frontDefault", pokemon.sprites.frontDefault)
                    put("backDefault", pokemon.sprites.backDefault)
                }
            }

            return Content(
                title = pokemon.name.replaceFirstChar { it.uppercase() },
                description = "포켓몬 #${pokemon.id} - ${pokemon.name.replaceFirstChar { it.uppercase() }}",
                imageUrl = pokemon.sprites.frontDefault,
                contentType = "POKEMON",
                body = body,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }
} 