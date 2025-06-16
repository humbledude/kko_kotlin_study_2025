package com.example.anika.pokemon.application.port.service

import com.example.anika.pokemon.application.port.incoming.FavoriteUseCase
import com.example.anika.pokemon.application.port.outcoming.FavoritePort
import com.example.anika.pokemon.application.port.outcoming.PokemonPort
import com.example.anika.pokemon.domain.Pokemon
import org.springframework.stereotype.Service

@Service
class FavoriteService(
    private val pokemonPort: PokemonPort,
    private val favoritePort: FavoritePort,
) : FavoriteUseCase {
    override fun addFavorite(pokemonId: Int): Boolean {
        return favoritePort.addByPokemonId(pokemonId)
    }

    override fun deleteFavorite(pokemonId: Int): Boolean {
        return favoritePort.deleteByPokemonId(pokemonId)
    }

    override fun getFavoriteList(): List<Pokemon> {
        val ids = favoritePort.getAllFavoritePokemonIds()
        return ids.map { pokemonPort.getPokemonById(it) }
    }

    override fun isFavorite(pokemonId: Int): Boolean {
        return favoritePort.existsByPokemonId(pokemonId)
    }
}
