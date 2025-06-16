package com.example.demo.pokemon.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "pokemon_favorite")
data class MyPokemon (
    @Id
    val id: Int,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val height: Int,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "TEXT", nullable = false)
    val sprites: PokemonSprite,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "TEXT", nullable = false)
    val stats : List<MyPokemonStat>
) {
    companion object {
        fun fromPokemon(pokemon: Pokemon) = MyPokemon(
            id = pokemon.id,
            name = pokemon.name,
            height = pokemon.height,
            sprites = pokemon.sprites,
            stats = pokemon.stats.map(MyPokemonStat::fromPokemonStat)
        )
    }
}