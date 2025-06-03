package kr.study.elan.kotlin.external

import kr.study.elan.kotlin.domain.PokemonApiDto
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Component
class PokemonClient(
    private val pokemonApiWebClient: WebClient,
) {
    fun fetchById(id: Int): Mono<PokemonApiDto> {
        return pokemonApiWebClient
            .get()
            .uri { uriBuilder ->
                uriBuilder.path("/pokemon/${id}")
                    .build()
            }
            .retrieve()
            .bodyToMono()
    }
}