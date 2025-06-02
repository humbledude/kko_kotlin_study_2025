package com.example.demo.pokemon

import com.example.demo.pokemon.model.MyPokemon
import com.example.demo.pokemon.model.MyPokemonStat
import com.example.demo.pokemon.model.PokemonSprite

object Fixtures {
    val bulbasaur = MyPokemon(
        id = 1,
        name = "bulbasaur",
        height = 7,
        sprites = PokemonSprite(
            backDefault = "backDefault",
            frontDefault = "frontDefault",
        ),
        stats = listOf(
            MyPokemonStat("hp", 30),
            MyPokemonStat("attack", 30),
        )
    )
}