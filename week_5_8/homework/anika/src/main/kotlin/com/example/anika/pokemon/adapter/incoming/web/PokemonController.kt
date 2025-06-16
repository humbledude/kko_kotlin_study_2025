package com.example.anika.pokemon.adapter.incoming.web

import com.example.anika.pokemon.application.port.incoming.PokemonUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pokemon")
class PokemonController(
    private val pokemonUseCase: PokemonUseCase,
) {
    @GetMapping("/{id}")
    fun getPokemon(
        @PathVariable id: Int,
    ): ResponseEntity<GetPokemonResponse> {
        val pokemon = pokemonUseCase.getPokemon(id)
        return ResponseEntity.ok(GetPokemonResponse.of(pokemon))
    }
}
