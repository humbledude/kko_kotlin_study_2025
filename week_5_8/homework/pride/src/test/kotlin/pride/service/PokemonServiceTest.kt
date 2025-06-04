package pride.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.web.reactive.function.client.WebClient
import pride.dto.PokemonResponse
import pride.domain.PokemonFavorite
import pride.repository.PokemonFavoriteRepository
import reactor.core.publisher.Mono

class PokemonServiceTest {
    private val webClient = mockk<WebClient>()
    private val pokemonFavoriteRepository = mockk<PokemonFavoriteRepository>()
    private val pokemonService = PokemonService(webClient, pokemonFavoriteRepository)

    @Test
    fun `포켓몬 정보 조회 성공`() {
        // given
        val pokemonId = 1
        val pokemonResponse = PokemonResponse(
            name = "bulbasaur",
            height = 7,
            id = 1,
            sprites = PokemonResponse.Sprites(
                frontDefault = "front_url",
                backDefault = "back_url"
            ),
            stats = listOf(
                PokemonResponse.Stat(
                    stat = PokemonResponse.StatInfo("hp"),
                    baseStat = 45
                )
            )
        )

        val requestHeadersUriSpec = mockk<WebClient.RequestHeadersUriSpec<*>>()
        val responseSpec = mockk<WebClient.ResponseSpec>()

        every { webClient.get() } returns requestHeadersUriSpec
        every { requestHeadersUriSpec.uri("/pokemon/$pokemonId") } returns requestHeadersUriSpec
        every { requestHeadersUriSpec.retrieve() } returns responseSpec
        every { responseSpec.bodyToMono(PokemonResponse::class.java) } returns Mono.just(pokemonResponse)

        // when
        val result = pokemonService.getPokemon(pokemonId)

        // then
        assert(result.name == "bulbasaur")
        assert(result.height == 7)
        verify { webClient.get() }
        verify { requestHeadersUriSpec.uri("/pokemon/$pokemonId") }
        verify { requestHeadersUriSpec.retrieve() }
        verify { responseSpec.bodyToMono(PokemonResponse::class.java) }
    }

    @Test
    fun `존재하지 않는 포켓몬 ID로 조회 시 에러`() {
        // given
        val pokemonId = 99999
        val requestHeadersUriSpec = mockk<WebClient.RequestHeadersUriSpec<*>>()
        val responseSpec = mockk<WebClient.ResponseSpec>()

        every { webClient.get() } returns requestHeadersUriSpec
        every { requestHeadersUriSpec.uri("/pokemon/$pokemonId") } returns requestHeadersUriSpec
        every { requestHeadersUriSpec.retrieve() } returns responseSpec

        // when & then
        assertThrows<RuntimeException> {
            pokemonService.getPokemon(pokemonId)
        }
    }

    @Test
    fun `즐겨찾기 추가 성공`() {
        // given
        val pokemonId = 1
        val pokemonResponse = PokemonResponse(
            name = "bulbasaur",
            height = 7,
            id = 1,
            sprites = PokemonResponse.Sprites(
                frontDefault = "front_url",
                backDefault = "back_url"
            ),
            stats = listOf(
                PokemonResponse.Stat(
                    stat = PokemonResponse.StatInfo("hp"),
                    baseStat = 45
                )
            )
        )

        val requestHeadersUriSpec = mockk<WebClient.RequestHeadersUriSpec<*>>()
        val responseSpec = mockk<WebClient.ResponseSpec>()

        every { webClient.get() } returns requestHeadersUriSpec
        every { requestHeadersUriSpec.uri("/pokemon/$pokemonId") } returns requestHeadersUriSpec
        every { requestHeadersUriSpec.retrieve() } returns responseSpec
        every { responseSpec.bodyToMono(PokemonResponse::class.java) } returns Mono.just(pokemonResponse)

        val expectedFavorite = PokemonFavorite(
            pokemonId = pokemonId,
            name = "bulbasaur",
            height = 7,
            frontImageUrl = "front_url",
            backImageUrl = "back_url"
        )

        every { pokemonFavoriteRepository.save(any()) } returns expectedFavorite
        every { pokemonFavoriteRepository.findByPokemonId(pokemonId) } returns expectedFavorite

        // when
        pokemonService.addFavorite(pokemonId)
        val result = pokemonService.getFavoriteByPokemonId(pokemonId)

        println("result: $result")

        // then
        assert(result?.name == "bulbasaur")
        assert(result?.pokemonId == pokemonId)
        verify { pokemonFavoriteRepository.save(any()) }
    }

    @Test
    fun `즐겨찾기 조회 실패 - 존재하지 않는 포켓몬 ID`() {
        // given
        val pokemonId = 99999
        every { pokemonFavoriteRepository.findByPokemonId(pokemonId) } returns null

        // when & then
        val result = pokemonService.getFavoriteByPokemonId(pokemonId)
        assert(result == null)
        verify { pokemonFavoriteRepository.findByPokemonId(pokemonId) }
    }

    @Test
    fun `즐겨찾기 목록 조회 성공`() {
        // given
        val favorites = listOf(
            PokemonFavorite(
                pokemonId = 1,
                name = "bulbasaur",
                height = 7,
                frontImageUrl = "front_url",
                backImageUrl = "back_url"
            ),
            PokemonFavorite(
                pokemonId = 2,
                name = "ivysaur",
                height = 10,
                frontImageUrl = "front_url2",
                backImageUrl = "back_url2"
            )
        )

        every { pokemonFavoriteRepository.findAll() } returns favorites

        // when
        val result = pokemonService.getFavorites()

        // then
        assert(result.size == 2)
        assert(result[0].name == "bulbasaur")
        assert(result[1].name == "ivysaur")
        verify { pokemonFavoriteRepository.findAll() }
    }
} 