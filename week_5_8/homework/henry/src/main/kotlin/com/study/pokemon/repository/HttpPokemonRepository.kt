package com.study.pokemon.repository

import com.study.pokemon.domain.PokemonRepository
import com.study.pokemon.domain.model.Pokemon
import com.study.pokemon.dto.request.HttpPokemonData
import com.study.pokemon.exception.CustomException
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Repository
class HttpPokemonRepository(
    val pokemonWebClient: WebClient,
) : PokemonRepository {
    override suspend fun getPokemonInfo(pokemonId: Int): Pokemon =
        pokemonWebClient
            .get()
            .uri("/pokemon/$pokemonId")
            .retrieve()
            .onRawStatus(
                { status -> status == 404 },
                { _ ->
                    Mono.error(
                        CustomException(
                            message = "포켓몬 정보를 가져오는 중 오류가 발생했습니다.",
                            status = HttpStatus.NOT_FOUND,
                        ),
                    )
                },
            ).bodyToMono(HttpPokemonData::class.java)
            .awaitSingle()
            .toDomain()
}
