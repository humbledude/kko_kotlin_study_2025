package com.joshua.feed.api

import com.joshua.feed.content.PokemonContentProvider
import com.joshua.feed.domain.content.Content
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pokemon-feed")
class PokemonFeedController(
    private val pokemonContentProvider: PokemonContentProvider
) {
    @GetMapping("/recommendations")
    suspend fun getRecommendedPokemonFeed(): List<Content> {
        return pokemonContentProvider.getContents(5)
    }
} 