package com.example.demo.pokemon

import com.example.demo.pokemon.model.MyPokemon
import com.example.demo.pokemon.model.MyPokemonStat
import com.example.demo.pokemon.model.Pokemon
import com.example.demo.pokemon.model.PokemonNotFoundException
import kotlinx.coroutines.runBlocking
import org.apache.coyote.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import reactor.core.publisher.Mono

@Service
class PokeApiService(
    @Qualifier("pokeWebClient") val client: WebClient,
    val myPokemonRepository: MyPokemonRepository
) {

    val log = LoggerFactory.getLogger(javaClass)


    fun getPokemonToMap(id: Int): Mono<Map<String, Any>> {
        return client.get()
            .uri("/api/v2/pokemon/{id}", id)
            .retrieve()
            .bodyToMono(object: ParameterizedTypeReference<Map<String, Any>>() {})
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
                log.error("4xx client error: " + response.statusCode().value())
                if (response.statusCode().value() == 404) {
                    Mono.error(PokemonNotFoundException("Pokemon $id is not found"))
                } else {
                    Mono.error(BadRequestException("Invalid request"))
                }
            }
            .awaitBody<Pokemon>()
    }

    suspend fun getMyPokemon(id: Int): MyPokemon {
        return MyPokemon.fromPokemon(getPokemon(id))
    }


    fun saveFavoritePokemon(id: Int) : Pair<MyPokemon, Boolean> {
        myPokemonRepository.findByIdOrNull(id)
            ?.let {
                return Pair(it, false)
            }

        runBlocking {
            getMyPokemon(id)
        }.let {
            myPokemonRepository.save(it)
            return Pair(it, true)
        }
    }

    fun getAllFavoritePokemon(): List<MyPokemon> {
        return myPokemonRepository.findAll()
    }
}