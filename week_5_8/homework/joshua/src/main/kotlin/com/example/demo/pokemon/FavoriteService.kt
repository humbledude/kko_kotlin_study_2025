package com.example.demo.pokemon

import com.example.demo.pokemon.model.MyPokemon
import kotlinx.coroutines.runBlocking
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FavoriteService (
    val pokeApiService: PokeApiService,
    val myPokemonRepository: MyPokemonRepository
){

    fun saveFavoritePokemon(id: Int) : Pair<MyPokemon, Boolean> {
        myPokemonRepository.findByIdOrNull(id)
            ?.let {
                return Pair(it, false)
            }

        runBlocking {
            pokeApiService.getMyPokemon(id)
        }.let {
            myPokemonRepository.save(it)
            return Pair(it, true)
        }
    }

    fun getAllFavoritePokemon(): List<MyPokemon> {
        return myPokemonRepository.findAll()
    }
}