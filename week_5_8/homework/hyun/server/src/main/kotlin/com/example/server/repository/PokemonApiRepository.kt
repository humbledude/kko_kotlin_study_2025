package com.example.server.repository

import com.example.server.dto.PokemonInfo
import com.example.server.dto.PokemonInfoApi
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient

@Repository
class PokemonApiRepository(
    private val webClient: WebClient = WebClient.builder()
        .baseUrl("https://pokeapi.co")
        .build()
) {

    fun getPocketMonsterInfoById(id: String): PokemonInfo {
        val pokemonInfoApi = webClient.get()
            .uri("/api/v2/pokemon/{id}", id)
            .retrieve()
            .bodyToMono(PokemonInfoApi::class.java)
            .block() ?: throw NullPointerException("Pokemon info not found")

        println(pokemonInfoApi)

        return PokemonInfoApi.toEntity(pokemonInfoApi)
    }
}