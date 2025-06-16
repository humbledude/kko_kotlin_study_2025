package com.example.server.controller

import com.example.server.dto.PokemonInfoDto
import com.example.server.service.PokemonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/pokemon")
@RestController
class PokemonController (
    private val pokemonService: PokemonService
) {

    @GetMapping("/info")
    fun getPocketMonster(@RequestParam("id") id: String?): PokemonInfoDto {
        if (id == null) {
            throw NullPointerException()
        }

        return pokemonService.getPocketMonsterInfoById(id)
    }

    @PostMapping("/favorite")
    fun addFavorite(@RequestParam("id") id: String?) {
        if (id == null) {
            throw NullPointerException()
        }

        pokemonService.addFavoritePokemon(id)
    }

    @GetMapping("/favorite")
    fun getFavoritePokemon(@RequestParam("page") page: Int = 0): List<PokemonInfoDto> {
        return pokemonService.getFavoritePokemonList(page)
    }

}