package pride.domain

import jakarta.persistence.*

@Entity
@Table(name="pokemon_favorites")
data class PokemonFavorite(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val pokemonId: Int,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val height: Int,

    @Column(nullable = true)
    val frontImageUrl: String,

    @Column(nullable = true)
    val backImageUrl: String
) 