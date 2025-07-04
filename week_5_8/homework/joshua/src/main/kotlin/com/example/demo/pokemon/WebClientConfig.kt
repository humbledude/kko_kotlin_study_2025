package com.example.demo.pokemon

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun pokeWebClient(): WebClient = WebClient.builder()
        .baseUrl("https://pokeapi.co")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .codecs { configurer ->
            configurer
                .defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024)  // 16MB
        }
        .build()
}