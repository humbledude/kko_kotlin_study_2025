package com.study.pokemon.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("pokemon_favorite")
data class PokemonFavorite(
    @Id
    val id: Int? = null,
    
    @Column("pokemon_id")
    val pokemonId: Int,
    
    @Column("pokemon_name")
    val pokemonName: String,
    
    @Column("pokemon_image")
    val pokemonImage: String,
    
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
) 