package pride.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import pride.dto.PokemonResponse
import pride.domain.PokemonFavorite
import pride.repository.PokemonFavoriteRepository

@Service
class PokemonService(
    private val webClient: WebClient,
    private val pokemonFavoriteRepository: PokemonFavoriteRepository
) {
    fun getPokemon(id: Int): PokemonResponse {
        return try {
            webClient.get()
                .uri("/pokemon/$id")
                .retrieve()
                .bodyToMono(PokemonResponse::class.java)
                .block() ?: throw RuntimeException("포켓몬을 찾을 수 없습니다.")
        } catch (e: WebClientResponseException.NotFound) {
            throw RuntimeException("포켓몬을 찾을 수 없습니다.")
        }
    }

    @Transactional
    fun addFavorite(pokemonId: Int): PokemonFavorite {
        val pokemon = getPokemon(pokemonId)
        return pokemonFavoriteRepository.save(
            PokemonFavorite(
                pokemonId = pokemon.id,
                name = pokemon.name,
                height = pokemon.height,
                frontImageUrl = pokemon.sprites.frontDefault,
                backImageUrl = pokemon.sprites.backDefault
            )
        )
    }

    @Transactional(readOnly = true)
    fun getFavorites(): List<PokemonFavorite> {
        return pokemonFavoriteRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun getFavoriteByPokemonId(pokemonId: Int): PokemonFavorite? {
        return pokemonFavoriteRepository.findByPokemonId(pokemonId)
    }
} 