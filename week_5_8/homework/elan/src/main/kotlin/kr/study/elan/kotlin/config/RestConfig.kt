package kr.study.elan.kotlin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class RestConfig {
    @Bean
    fun pokemonApiWebClient(): WebClient {
        val bufferSizeInBytes = 16 * 1024 * 1024
        val exchangeStrategies = ExchangeStrategies.builder()
            .codecs { configurer: ClientCodecConfigurer? ->
                configurer!!
                    .defaultCodecs()
                    .maxInMemorySize(bufferSizeInBytes)
            }
            .build()

        return WebClient.builder()
            .baseUrl("https://pokeapi.co/api/v2")
            .exchangeStrategies(exchangeStrategies)
            .build()
    }
}