package com.example.anika.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "pokeapi")
data class PokeApiProperties(
    val baseUrl: String,
)
