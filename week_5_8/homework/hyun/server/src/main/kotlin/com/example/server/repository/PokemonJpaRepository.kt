package com.example.server.repository

import com.example.server.dto.PokemonInfoDb
import com.example.server.dto.PokemonInfoDto
import org.springframework.data.jpa.repository.JpaRepository

interface PokemonJpaRepository : JpaRepository<PokemonInfoDb, Int>