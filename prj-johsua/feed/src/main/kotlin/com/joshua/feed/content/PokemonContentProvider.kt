package com.joshua.feed.content

import com.joshua.feed.domain.content.Content
import com.joshua.feed.pokemon.PokeApiService
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class PokemonContentProvider(
    private val pokeApiService: PokeApiService
) : ContentProvider {
    
    companion object {
        private const val MAX_POKEMON_ID = 898  // 현재 포켓몬 API에서 사용 가능한 최대 ID
    }

    override suspend fun getContent(id: String): Content {
        val pokemon = pokeApiService.getPokemon(id.toInt())
        return PokemonContent(pokemon).content
    }

    override suspend fun getRandomContent(): Content {
        val randomId = Random.nextInt(1, MAX_POKEMON_ID + 1)
        return getContent(randomId.toString())
    }

    override suspend fun getContents(count: Int): List<Content> {
        return (1..count).map {
            getRandomContent()
        }
    }
} 