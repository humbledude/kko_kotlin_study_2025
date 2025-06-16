package com.study.pokemon.service

import com.study.pokemon.domain.PokemonFavoriteRepository
import com.study.pokemon.domain.model.PokemonFavorite
import com.study.pokemon.exception.CustomException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class PokemonFavoriteService(
    private val pokemonFavoriteRepository: PokemonFavoriteRepository,
    private val pokemonService: PokemonService
) {
    suspend fun addFavorite(pokemonId: Int): PokemonFavorite {
        if (pokemonId < 0) {
            throw CustomException("포켓몬 ID는 양수여야 하는데요.", status = HttpStatus.BAD_REQUEST)
        }

        val existing = pokemonFavoriteRepository.findByPokemonId(pokemonId)
        if (existing != null) {
            return existing
        }
        
        val pokemon = pokemonService.getPokemonInfo(pokemonId)
        val favorite = PokemonFavorite(
            pokemonId = pokemon.id,
            pokemonName = pokemon.name,
            pokemonImage = pokemon.sprites.frontDefault
        )
        
        return pokemonFavoriteRepository.save(favorite)
    }
    
    suspend fun getFavorites(): List<PokemonFavorite> {
        return pokemonFavoriteRepository.findAll()
    }
} 