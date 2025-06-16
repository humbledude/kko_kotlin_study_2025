package com.example.anika.pokemon.adapter.outcoming.web

import com.example.anika.pokemon.application.port.outcoming.PokemonPort
import com.example.anika.pokemon.domain.Pokemon
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class PokeApiAdapter(
    private val webClient: WebClient,
) : PokemonPort {
    override fun getPokemonById(id: Int): Pokemon {
        return runCatching {
            webClient
                .get()
                .uri(URI_GET_POKEMON_BY_ID, id)
                .retrieve()
                .bodyToMono(PokeApiResponse::class.java)
                .block()
                ?.toPokemon()
                ?: throw IllegalStateException("PokeApi 에러 (id=$id)")
        }.getOrElse { e ->
            throw IllegalStateException("PokeApi 에러 (id=$id)", e)
        }
    }

    companion object {
        private const val URI_GET_POKEMON_BY_ID = "/api/v2/pokemon/{id}"
    }
}
