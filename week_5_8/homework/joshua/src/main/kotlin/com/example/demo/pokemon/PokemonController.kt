package com.example.demo.pokemon

import com.example.demo.SimpleResponse
import com.example.demo.pokemon.model.FavoriteRequest
import com.example.demo.pokemon.model.FavoriteResponse
import com.example.demo.pokemon.model.MyPokemon
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pokemon")
class PokemonController(
    val pokeApiService: PokeApiService
) {

    @GetMapping("/{id}")
    fun getMyPokemon(@PathVariable id: Int) : MyPokemon {
        return runBlocking {
            pokeApiService.getMyPokemon(id)
        }
    }

    @GetMapping("/reactive/{id}")
    suspend fun getReactiveMyPokemon(@PathVariable id: Int) : MyPokemon {
        return pokeApiService.getMyPokemon(id)
    }

    @PostMapping("/favorites")
    fun postFavoritePokemon(@RequestBody request: FavoriteRequest) : ResponseEntity<SimpleResponse>{
        val (pokemon, saved) = pokeApiService.saveFavoritePokemon(request.id)
        if (saved) {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body( SimpleResponse(
                    status = HttpStatus.CREATED.value(),
                    message = "[${pokemon.id}] ${pokemon.name} 이 잘 저장되었어요 "
                )
                )
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(SimpleResponse(
                    status = HttpStatus.CONFLICT.value(),
                    message ="[${pokemon.id}] ${pokemon.name} 이 이미 저장되어있어요 "
                )
                )
        }
    }

    @GetMapping("/favorites")
    fun getAllFavoritePokemon(): FavoriteResponse {
        return FavoriteResponse(pokeApiService.getAllFavoritePokemon())
    }

}
