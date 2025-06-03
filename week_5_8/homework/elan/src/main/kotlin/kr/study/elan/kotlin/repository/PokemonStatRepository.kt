package kr.study.elan.kotlin.repository

import kr.study.elan.kotlin.entity.PokemonStat
import org.springframework.data.jpa.repository.JpaRepository

interface PokemonStatRepository: JpaRepository<PokemonStat, Long>