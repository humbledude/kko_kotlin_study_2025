package com.example.server.service

import com.example.server.dto.PokemonInfo
import com.example.server.dto.PokemonInfoApi
import com.example.server.dto.PokemonInfoDto
import com.example.server.dto.PokemonSprites
import com.example.server.dto.PokemonStat
import com.example.server.dto.PokemonType
import com.example.server.dto.PokemonTypeSlot
import com.example.server.dto.StatDetail
import com.example.server.repository.PokemonApiRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class PokemonServiceTest {

    @MockK
    lateinit var pokemonApiRepository: PokemonApiRepository

    @InjectMockKs
    lateinit var pokemonService: PokemonService

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getPokemon() {
        // given
        val defaultData = PokemonInfoApi.toEntity(getDefaultData())
        every { pokemonApiRepository.getPocketMonsterInfoById(any()) } returns defaultData

        // when & then
        assertEquals(PokemonInfoDto.valueOf(defaultData), pokemonService.getPocketMonsterInfoById(defaultData.number.toString()))
    }

    fun getDefaultData(): PokemonInfoApi {
        return PokemonInfoApi(
            id = 2,
            name = "ivysaur",
            height = 10,
            weight = 130,
            types = listOf(
                PokemonTypeSlot(
                    slot = 1,
                    type = PokemonType(
                        name = "grass",
                        url = "https://pokeapi.co/api/v2/type/12/"
                    )
                ),
                PokemonTypeSlot(
                    slot = 2,
                    type = PokemonType(
                        name = "poison", "url=https://pokeapi.co/api/v2/type/4/"
                    )
                )
            ),
            stats = listOf(
                PokemonStat(
                    baseStat = 60,
                    effort = 0,
                    stat = StatDetail(name = "hp", url = "https://pokeapi.co/api/v2/stat/1/")
                ),
                PokemonStat(
                    baseStat = 62,
                    effort = 0,
                    stat = StatDetail(name = "attack", url = "https://pokeapi.co/api/v2/stat/2/")
                ),
                PokemonStat(
                    baseStat = 63,
                    effort = 0,
                    stat = StatDetail(name = "defense", url = "https://pokeapi.co/api/v2/stat/3/")
                ),
                PokemonStat(
                    baseStat = 80,
                    effort = 1,
                    stat = StatDetail(name = "special-attack", url = "https://pokeapi.co/api/v2/stat/4/")
                ),
                PokemonStat(
                    baseStat = 80,
                    effort = 1,
                    stat = StatDetail(name = "special-defense", url = "https://pokeapi.co/api/v2/stat/5/")
                ),
                PokemonStat(
                    baseStat = 60,
                    effort = 0,
                    stat = StatDetail(name = "speed", url = "https://pokeapi.co/api/v2/stat/6/")
                )
            ),
            sprites = PokemonSprites(
                frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png",
                backDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/2.png"
            )
        )

    }

}