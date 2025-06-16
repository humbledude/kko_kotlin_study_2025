package kr.study.elan.kotlin.service

import kr.study.elan.kotlin.domain.PokemonApiDto
import kr.study.elan.kotlin.external.PokemonClient
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Service
class PokemonService(
    private val pokemonClient: PokemonClient
) {
    fun fetchPokemon(id: Int): Mono<PokemonApiDto> {
        return pokemonClient.fetchById(id)
    }


}