package com.example.anika.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(PokeApiProperties::class)
class WebClientConfig {
    @Bean
    fun webClient(pokeApiProperties: PokeApiProperties): WebClient {
        val exchangeStrategies =
            ExchangeStrategies
                .builder()
                .codecs { config ->
                    config.defaultCodecs().maxInMemorySize(1 * 1024 * 1024)
                }.build()

        return WebClient
            .builder()
            .baseUrl(pokeApiProperties.baseUrl)
            .exchangeStrategies(exchangeStrategies)
            .build()
    }
}
