package kr.study.elan.kotlin.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "pokemon")
data class Pokemon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "pokemon_id",
        unique = true)
    val pokemonId: Int,
    val name: String,
    val height: Int,
    val frontSprite: String,
    val backendSprite: String,

    @OneToMany(mappedBy = "pokemon", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val stats: MutableList<PokemonStat> = mutableListOf()
)

@Entity
@Table(name = "pokemon_stat")
data class PokemonStat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val name: String,
    val baseStat: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "pokemon_id",
    )
    var pokemon: Pokemon
)