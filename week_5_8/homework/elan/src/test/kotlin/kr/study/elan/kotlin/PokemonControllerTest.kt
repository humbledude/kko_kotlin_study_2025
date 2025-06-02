package kr.study.elan.kotlin

import kr.study.elan.kotlin.controller.PokemonController
import kr.study.elan.kotlin.service.PokemonService
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import kotlin.test.Test

//@WebMvcTest(PokemonController::class)
@WebFluxTest(PokemonController::class)
class PokemonControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockitoBean
    lateinit var pokemonService: PokemonService

    @Test
    fun `포켓몬 id로 요청하기`() {

//{
//    "id": 35,
//    "is_default": true,
//    "location_area_encounters": "https://pokeapi.co/api/v2/pokemon/35/encounters"
//}
        val output = mapOf(
            "id" to 35,
            "is_default" to true,
            "location_area_encounters" to "https://pokeapi.co/api/v2/pokemon/35/encounters"
        )

        given(pokemonService.getPokemon(anyInt())).will {
            Mono.just(output)
        }

        // 안됨
//        mockMvc.perform(get("/pokemon/35"))
//            .andExpect(status().isOk())
//            .andDo { it -> {
//                print(it.response.contentAsString.contains("id"))
//            } }
        webTestClient.get().uri("/pokemon/35")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath(".id").isEqualTo(35)
    }

    @Test
    fun `포켓몬 id는 숫자가 아니면 400 반환`() {
        val output = mapOf(
            "id" to 35,
            "is_default" to true,
            "location_area_encounters" to "https://pokeapi.co/api/v2/pokemon/35/encounters"
        )

        given(pokemonService.getPokemon(anyInt())).will {
            Mono.just(output)
        }

        webTestClient.get().uri("/pokemon/invalid_id")
            .exchange()
            .expectStatus().isBadRequest()
    }
}