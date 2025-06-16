package pride.repository

import org.springframework.data.jpa.repository.JpaRepository
import pride.domain.PokemonFavorite

interface PokemonFavoriteRepository : JpaRepository<PokemonFavorite, Long> {
    fun save(pokemonFavorite: PokemonFavorite): PokemonFavorite
    fun findByPokemonId(pokemonId: Int): PokemonFavorite?
    override fun findAll(): List<PokemonFavorite>
} 