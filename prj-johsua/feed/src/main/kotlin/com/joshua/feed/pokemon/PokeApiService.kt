package com.joshua.feed.pokemon

import com.joshua.feed.pokemon.model.Pokemon
import com.joshua.feed.pokemon.model.PokemonNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.awaitBody
import reactor.core.publisher.Mono

@Service
class PokeApiService(
    @Qualifier("pokeWebClient") private val client: WebClient
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun getPokemonToMap(id: Int): Mono<Map<String, Any>> {
        return client.get()
            .uri("/api/v2/pokemon/{id}", id)
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<Map<String, Any>>() {})
    }

    fun getPokemonMono(id: Int): Mono<Pokemon> {
        return client.get()
            .uri("/api/v2/pokemon/{id}", id)
            .retrieve()
            .bodyToMono(Pokemon::class.java)
    }

    suspend fun getPokemon(id: Int): Pokemon {
        return client.get()
            .uri("/api/v2/pokemon/{id}", id)
            .retrieve()
            .onStatus({ it.is4xxClientError }) { response ->
                log.error("4xx client error: ${response.statusCode().value()}")
                if (response.statusCode().value() == 404) {
                    Mono.error(PokemonNotFoundException("Pokemon $id is not found"))
                } else {
                    Mono.error(WebClientResponseException(
                        response.statusCode().value(),
                        "Invalid request",
                        response.headers().asHttpHeaders(),
                        null,
                        null
                    ))
                }
            }
            .awaitBody<Pokemon>()
    }
} 