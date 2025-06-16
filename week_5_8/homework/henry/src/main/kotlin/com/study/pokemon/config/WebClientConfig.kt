package com.study.pokemon.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
    @Bean
    fun pokemonWebClient(): WebClient =
        WebClient
            .builder()
            .baseUrl("https://pokeapi.co/api/v2")
            .build()
}
