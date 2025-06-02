package com.example.demo.pokemon

import com.example.demo.pokemon.model.MyPokemon
import com.example.demo.pokemon.model.Pokemon
import com.example.demo.pokemon.model.PokemonNotFoundException
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

class PokeApiServiceTest {

    val pokeWebClient = WebClientConfig().pokeWebClient()
    val service = PokeApiService(pokeWebClient)


    @Test
    fun `기본 map 으로 받아보기`() {
        val resp : Map<String, Any>? = service.getPokemonToMap(1).block(3.seconds.toJavaDuration())

        assertEquals("bulbasaur", resp?.get("name"))
        assertEquals(7, resp?.get("height"))
        val sprites: Map<String, Any> = resp?.get("sprites") as Map<String, Any>
        println(sprites)
        assertNotNull(sprites)
        val stats: List<Map<String, Any>> = resp?.get("stats") as List<Map<String, Any>>
        println(stats)
        assertNotNull(stats)
    }

    @Test
    fun `mono 로 받아보기`() {
        val resp : Pokemon? = service.getPokemonMono(1).block(3.seconds.toJavaDuration())

        assertEquals("bulbasaur", resp?.name)
        assertEquals(7, resp?.height)
        assertNotNull(resp?.sprites?.frontDefault)
        assertNotNull(resp?.sprites?.backDefault)
        assertNotNull(resp?.stats?.get(0)?.stat?.name)
        assertNotNull(resp?.stats?.get(0)?.baseStat)
    }

    @Test
    fun `suspend 로 받아보기`() {
        val resp : Pokemon? = runBlocking {
            service.getPokemon(1)
        }

        assertEquals("bulbasaur", resp?.name)
        assertEquals(7, resp?.height)
        assertNotNull(resp?.sprites?.frontDefault)
        assertNotNull(resp?.sprites?.backDefault)
        assertNotNull(resp?.stats?.get(0)?.stat?.name)
        assertNotNull(resp?.stats?.get(0)?.baseStat)
    }

    @Test
    fun `MyPokemon 으로 받아보기`() {
        val resp : MyPokemon? = runBlocking {
            service.getMyPokemon(1)
        }

        assertEquals("bulbasaur", resp?.name)
        assertEquals(7, resp?.height)
        assertNotNull(resp?.sprites?.frontDefault)
        assertNotNull(resp?.sprites?.backDefault)
        assertNotNull(resp?.stats?.get(0)?.name)
        assertNotNull(resp?.stats?.get(0)?.baseStat)

    }

    @Test
    fun `없는 포켓몬찾기`() {
        assertFailsWith<PokemonNotFoundException> {
            runBlocking {
                service.getMyPokemon(12313123)
            }
        }
    }



}