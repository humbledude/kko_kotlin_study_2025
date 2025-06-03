package com.study.pokemon.controller

import com.study.pokemon.domain.model.Pokemon
import com.study.pokemon.service.PokemonService
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PokemonInfoController(
    private val pokemonService: PokemonService,
) {
    @GetMapping("/pokemon/{id}")
    suspend fun getPokemonInfo(
        @PathVariable("id") pokemonId: Int,
    ): ResponseEntity<Pokemon> {
            val pokemonInfo = pokemonService.getPokemonInfo(pokemonId)
            return ResponseEntity
                .ok()
                .body(pokemonInfo)
        }
}
