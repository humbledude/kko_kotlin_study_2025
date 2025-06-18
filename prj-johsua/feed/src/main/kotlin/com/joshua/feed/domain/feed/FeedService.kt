package com.joshua.feed.domain.feed

import com.joshua.feed.domain.content.Content
import com.joshua.feed.pokemon.PokemonContentProvider
import org.springframework.stereotype.Service

@Service
class FeedService(
    private val pokemonContentProvider: PokemonContentProvider
) {
    suspend fun getUserFeed(username: String, page: Int, size: Int): List<Content> {
        // TODO: username, page, size 활용한 실제 추천/페이징/중복방지 구현
        return pokemonContentProvider.getContents(size)
    }
} 