package kr.study.elan.kotlin.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Service
class PokemonService(
    private val pokemonWebClient: WebClient
) {
    fun getPokemon(id: Int): Mono<Any> {
        return pokemonWebClient
            .get()
            .uri { uriBuilder ->
                uriBuilder.path("/pokemon/${id}")
                    .build()
            }
            .retrieve()
            .bodyToMono<Any>()
    }
}