package com.example.demo.pokemon

import com.example.demo.pokemon.model.MyPokemon
import com.example.demo.pokemon.model.MyPokemonStat
import com.example.demo.pokemon.model.PokemonSprite
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.Test
import kotlin.test.assertEquals

@DataJpaTest
class MyPokemonRepositoryTest @Autowired constructor(
    val myPokemonRepository: MyPokemonRepository
) {

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

    @Test
    fun `save and find by id`() {
        // given
        val pokeomon = bulbasaur

        // when
        val saved = myPokemonRepository.save(pokeomon)
        val found = myPokemonRepository.findById(1).orElseThrow()


        // then
        assertEquals(saved.id, found.id)
        assertEquals("bulbasaur", found.name)
        assertEquals("backDefault", found.sprites.backDefault)
        assertEquals(30, found.stats[0].baseStat)
    }
}