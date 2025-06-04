package pride.controller

import pride.dto.PokemonResponse
import pride.domain.PokemonFavorite
import pride.service.PokemonService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pokemon")
class PokemonController(
    private val pokemonService: PokemonService
) {
    @GetMapping("/{id}")
    fun getPokemon(@PathVariable id: Int): PokemonResponse {
        return pokemonService.getPokemon(id)
    }

    @PostMapping("/favorite/{id}")
    fun addFavorite(@PathVariable id: Int): PokemonFavorite {
        return pokemonService.addFavorite(id)
    }

    @GetMapping("/favorites")
    fun getFavorites(): List<PokemonFavorite> {
        return pokemonService.getFavorites()
    }
} 