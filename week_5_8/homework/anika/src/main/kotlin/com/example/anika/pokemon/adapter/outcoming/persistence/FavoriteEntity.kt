package com.example.anika.pokemon.adapter.outcoming.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "favorites")
class FavoriteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "pokemon_id", nullable = false, unique = true)
    val pokemonId: Int,
) {
    private constructor() : this(null, 0)
}
