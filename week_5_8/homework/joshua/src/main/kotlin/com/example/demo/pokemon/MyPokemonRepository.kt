package com.example.demo.pokemon

import com.example.demo.pokemon.model.MyPokemon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MyPokemonRepository: JpaRepository<MyPokemon, Int> {

}