package kr.study.elan.kotlin.repository

import kr.study.elan.kotlin.entity.Pokemon
import org.springframework.data.jpa.repository.JpaRepository

interface PokemonRepository: JpaRepository<Pokemon, Long>