package pride.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pride.dto.PokemonResponse
import pride.domain.PokemonFavorite
import pride.service.PokemonService

@WebMvcTest(PokemonController::class)
class PokemonControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var pokemonService: PokemonService

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

        every { pokemonService.getPokemon(pokemonId) } returns pokemonResponse

        // when & then
        mockMvc.perform(get("/api/pokemon/$pokemonId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("bulbasaur"))
            .andExpect(jsonPath("$.height").value(7))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.sprites.frontDefault").value("front_url"))
            .andExpect(jsonPath("$.sprites.backDefault").value("back_url"))
            .andExpect(jsonPath("$.stats[0].stat.name").value("hp"))
            .andExpect(jsonPath("$.stats[0].baseStat").value(45))
    }

    @Test
    fun `잘못된 포켓몬 ID로 조회 시 에러`() {
        // given
        val pokemonId = 99999
        every { pokemonService.getPokemon(pokemonId) } throws RuntimeException("포켓몬을 찾을 수 없습니다.")

        // when & then
        mockMvc.perform(get("/api/pokemon/$pokemonId"))
            .andExpect(status().isInternalServerError)
    }

    @Test
    fun `즐겨찾기 추가 성공`() {
        // given
        val pokemonId = 1
        val favorite = PokemonFavorite(
            pokemonId = pokemonId,
            name = "bulbasaur",
            height = 7,
            frontImageUrl = "front_url",
            backImageUrl = "back_url"
        )

        every { pokemonService.addFavorite(pokemonId) } returns favorite

        // when & then
        mockMvc.perform(post("/api/pokemon/favorite/$pokemonId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("bulbasaur"))
            .andExpect(jsonPath("$.pokemonId").value(1))
            .andExpect(jsonPath("$.height").value(7))
            .andExpect(jsonPath("$.frontImageUrl").value("front_url"))
            .andExpect(jsonPath("$.backImageUrl").value("back_url"))
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

        every { pokemonService.getFavorites() } returns favorites

        // when & then
        mockMvc.perform(get("/api/pokemon/favorites"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value("bulbasaur"))
            .andExpect(jsonPath("$[0].pokemonId").value(1))
            .andExpect(jsonPath("$[1].name").value("ivysaur"))
            .andExpect(jsonPath("$[1].pokemonId").value(2))
    }
} 