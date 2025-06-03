package kr.study.elan.kotlin.service

import kr.study.elan.kotlin.domain.PokemonApiDto
import kr.study.elan.kotlin.domain.PokemonResponseDto
import kr.study.elan.kotlin.entity.Pokemon
import kr.study.elan.kotlin.entity.PokemonStat
import kr.study.elan.kotlin.external.PokemonClient
import kr.study.elan.kotlin.repository.PokemonRepository
import kr.study.elan.kotlin.repository.PokemonStatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PokemonFavoriteService(
    private val pokemonRepository: PokemonRepository,
    private val pokemonStatRepository: PokemonStatRepository,
    private val pokemonClient: PokemonClient,
) {

    fun saveWithPokemon(id: Int): Long {
        val pokemonApiDto = requireNotNull(pokemonClient.fetchById(id).block())
        val pokemonEntity = pokemonApiDto.toEntity()
        val savedPokemon = pokemonRepository.save(pokemonEntity)

        val pokemonStatsEntity = pokemonApiDto.stats.map { statResponse ->
            PokemonStat(
                baseStat = statResponse.baseStat,
                name = statResponse.stat.name,
                pokemon = savedPokemon
            )
        }
        pokemonStatRepository.saveAll(pokemonStatsEntity)
        savedPokemon.stats.addAll(pokemonStatsEntity)
        val save = pokemonRepository.save(pokemonEntity)
        return save.id
    }

    fun PokemonApiDto.toEntity(): Pokemon =
        Pokemon(
            pokemonId = id,
            name = name,
            height = height,
            frontSprite = sprite.frontDefault,
            backendSprite = sprite.backDefault,
        )


    @Transactional(readOnly = true)
    fun getAll(): Collection<PokemonResponseDto> {
        return pokemonRepository.findAll().map {
            it.toResponseDto()
        }
    }

    fun Pokemon.toResponseDto(): PokemonResponseDto =
        PokemonResponseDto(
            id = this.id,
            pokemonId = this.pokemonId,
            name = this.name,
            height = this.height,
            frontSprite = this.frontSprite,
            backendSprite = this.backendSprite,
            stats = this.stats.map { it.toResponseDto() }
        )

    fun PokemonStat.toResponseDto(): PokemonResponseDto.PokemonStat =
        PokemonResponseDto.PokemonStat(
            name = this.name,
            baseStat = this.baseStat
        )
}
