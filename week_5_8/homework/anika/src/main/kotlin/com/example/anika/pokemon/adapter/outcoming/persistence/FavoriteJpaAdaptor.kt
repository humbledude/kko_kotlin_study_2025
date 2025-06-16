package com.example.anika.pokemon.adapter.outcoming.persistence

import com.example.anika.pokemon.application.port.outcoming.FavoritePort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FavoriteJpaAdaptor(
    private val favoriteJpaRepository: FavoriteJpaRepository,
) : FavoritePort {
    @Transactional
    override fun addByPokemonId(pokemonId: Int): Boolean {
        if (existsByPokemonId(pokemonId)) {
            return false
        }
        favoriteJpaRepository.save(FavoriteEntity(pokemonId = pokemonId))
        return true
    }

    @Transactional
    override fun deleteByPokemonId(pokemonId: Int): Boolean {
        if (!existsByPokemonId(pokemonId)) {
            return false
        }
        favoriteJpaRepository.deleteByPokemonId(pokemonId)
        return true
    }

    override fun getAllFavoritePokemonIds(): List<Int> {
        return favoriteJpaRepository.findAll().map { it.pokemonId }
    }

    override fun existsByPokemonId(pokemonId: Int): Boolean {
        return favoriteJpaRepository.existsByPokemonId(pokemonId)
    }
}
