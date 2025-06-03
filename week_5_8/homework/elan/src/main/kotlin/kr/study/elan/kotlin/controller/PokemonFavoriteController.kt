package kr.study.elan.kotlin.controller

import kr.study.elan.kotlin.domain.PokemonResponseDto
import kr.study.elan.kotlin.service.PokemonFavoriteService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/favorites")
class PokemonFavoriteController(
    private val pokemonFavoriteService: PokemonFavoriteService
) {

    @PostMapping("/pokemon/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveWithPokemon(@PathVariable id: Int): CreatedPokemonFavorite {
        return CreatedPokemonFavorite(pokemonFavoriteService.saveWithPokemon(id))
    }

    @GetMapping("")
    fun allFavorites(): Collection<PokemonResponseDto>? {
        return pokemonFavoriteService.getAll()
    }

    data class CreatedPokemonFavorite(
        val id: Any,
    )
}