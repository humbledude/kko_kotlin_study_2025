package com.study.pokemon.controller

import com.study.pokemon.domain.model.PokemonFavorite
import com.study.pokemon.service.PokemonFavoriteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pokemon/favorite")
class PokemonFavoriteController(
    private val pokemonFavoriteService: PokemonFavoriteService
) {
    @PostMapping("/{id}")
    suspend fun addFavorite(
        @PathVariable("id") pokemonId: Int
    ): ResponseEntity<PokemonFavorite> {
        val favorite = pokemonFavoriteService.addFavorite(pokemonId)
        return ResponseEntity.ok(favorite)
    }
    
    @GetMapping
    suspend fun getFavorites(): ResponseEntity<List<PokemonFavorite>> {
        val favorites = pokemonFavoriteService.getFavorites()
        return ResponseEntity.ok(favorites)
    }
} 