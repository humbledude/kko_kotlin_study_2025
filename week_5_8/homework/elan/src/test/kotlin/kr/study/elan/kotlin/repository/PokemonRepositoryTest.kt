package kr.study.elan.kotlin.repository

import kr.study.elan.kotlin.entity.Pokemon
import kr.study.elan.kotlin.entity.PokemonStat
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class PokemonRepositoryTest @Autowired constructor(
    val pokemonRepository: PokemonRepository,
    val pokemonStatRepository: PokemonStatRepository
) {

    @Test
    fun `Pokemon과 Stat을 저장하고 조회할 수 있다`() {
        val pokemon = Pokemon(
            pokemonId = 25,
            name = "Pikachu",
            height = 4,
            frontSprite = "front.png",
            backendSprite = "back.png"
        )

        val savedPokemon = pokemonRepository.save(pokemon)

        val stat1 = PokemonStat(name = "speed", baseStat = 90, pokemon = savedPokemon)
        val stat2 = PokemonStat(name = "attack", baseStat = 55, pokemon = savedPokemon)

        val stats = listOf(stat1, stat2)
        pokemonStatRepository.saveAll(stats)
        savedPokemon.stats.addAll(stats)
        pokemonRepository.save(savedPokemon)

        val result = pokemonRepository.findById(savedPokemon.id).get()
        assertThat(result.name).isEqualTo("Pikachu")
        assertThat(result.stats).hasSize(2)
        assertThat(result.stats.map { it.name }).containsExactlyInAnyOrder("speed", "attack")
    }
}