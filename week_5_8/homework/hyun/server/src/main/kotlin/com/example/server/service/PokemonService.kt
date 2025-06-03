package com.example.server.service

import com.example.server.dto.PokemonInfoDb
import com.example.server.dto.PokemonInfoDto
import com.example.server.repository.PokemonApiRepository
import com.example.server.repository.PokemonJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class PokemonService(
    private val pokemonApiRepository: PokemonApiRepository,
    private val pokemonJpaRepository: PokemonJpaRepository
) {

    fun getPocketMonsterInfoById(id: String): PokemonInfoDto {
        val pokemonInfo = pokemonApiRepository.getPocketMonsterInfoById(id)

        return PokemonInfoDto.valueOf(pokemonInfo)
    }

    fun addFavoritePokemon(id: String) {
        val pokemonInfo = pokemonApiRepository.getPocketMonsterInfoById(id)

        // 동일 키값에 대해 update로 간주 (existsById로 사전 검증 필요. 쿼리 로그상 select를 하고 insert 쿼리 자체를 안날렸음)
        pokemonJpaRepository.save(PokemonInfoDb.valueOf(pokemonInfo))
    }

    fun getFavoritePokemonList(page: Int): List<PokemonInfoDto> {
        val pageable: Pageable = PageRequest.of(page, 10, Sort.by("number").ascending())
        return pokemonJpaRepository.findAll(pageable).content.map { item ->
            PokemonInfoDto.valueOf(pokemonApiRepository.getPocketMonsterInfoById(item.number.toString()))
        }
    }
}