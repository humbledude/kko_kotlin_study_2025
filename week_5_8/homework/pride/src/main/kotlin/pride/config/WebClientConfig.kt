package pride.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient {
        val maxInMemorySize = 1024 * 1024

        val strategies = ExchangeStrategies.builder()
            .codecs { configurer ->
                configurer.defaultCodecs().maxInMemorySize(maxInMemorySize)
            }
            .build()

        return WebClient.builder()
            .baseUrl("https://pokeapi.co/api/v2")
            .exchangeStrategies(strategies)
            .build()
    }
}