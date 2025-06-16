package com.example.anika.pokemon.adapter.incoming.web

import com.example.anika.pokemon.application.port.incoming.FavoriteUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/favorites")
class FavoriteController(
    private val favoriteUseCase: FavoriteUseCase,
) {
    @GetMapping
    fun getFavorites(): ResponseEntity<List<GetPokemonResponse>> {
        val favoritePokemonList = favoriteUseCase.getFavoriteList().map { GetPokemonResponse.of(it) }
        return ResponseEntity.ok(favoritePokemonList)
    }

    @PostMapping("/{pokemonId}")
    fun addFavorite(
        @PathVariable pokemonId: Int,
    ): ResponseEntity<List<GetPokemonResponse>> {
        if (!favoriteUseCase.addFavorite(pokemonId)) {
            return ResponseEntity.badRequest().build()
        }

        val favoritePokemonList = favoriteUseCase.getFavoriteList().map { GetPokemonResponse.of(it) }
        return ResponseEntity.ok(favoritePokemonList)
    }

    @DeleteMapping("/{pokemonId}")
    fun deleteFavorite(
        @PathVariable pokemonId: Int,
    ): ResponseEntity<List<GetPokemonResponse>> {
        if (!favoriteUseCase.deleteFavorite(pokemonId)) {
            return ResponseEntity.badRequest().build()
        }

        val favoritePokemonList = favoriteUseCase.getFavoriteList().map { GetPokemonResponse.of(it) }
        return ResponseEntity.ok(favoritePokemonList)
    }

    @GetMapping("/{pokemonId}/exists")
    fun isFavorite(
        @PathVariable pokemonId: Int,
    ): ResponseEntity<Boolean> {
        val exists = favoriteUseCase.isFavorite(pokemonId)
        return ResponseEntity.ok(exists)
    }
}
