package com.study.pokemon.repository

import com.study.pokemon.domain.PokemonFavoriteRepository
import com.study.pokemon.domain.model.PokemonFavorite
import kotlinx.coroutines.flow.toList
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
class R2dbcPokemonFavoriteRepository(
    private val coroutineRepository: CoroutinePokemonFavoriteRepository
) : PokemonFavoriteRepository {
    
    override suspend fun save(pokemonFavorite: PokemonFavorite): PokemonFavorite {
        return coroutineRepository.save(pokemonFavorite)
    }
    
    override suspend fun findAll(): List<PokemonFavorite> {
        return coroutineRepository.findAll().toList()
    }
    
    override suspend fun findByPokemonId(pokemonId: Int): PokemonFavorite? {
        return coroutineRepository.findByPokemonId(pokemonId)
    }
}

interface CoroutinePokemonFavoriteRepository : CoroutineCrudRepository<PokemonFavorite, Int> {
    suspend fun findByPokemonId(pokemonId: Int): PokemonFavorite?
}