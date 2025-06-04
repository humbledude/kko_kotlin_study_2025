package pride.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import pride.domain.PokemonFavorite
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DataJpaTest
class PokemonFavoriteRepositoryTest {
    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var pokemonFavoriteRepository: PokemonFavoriteRepository

    @Test
    fun `즐겨찾기 저장 및 조회 성공`() {
        // given
        val favorite = PokemonFavorite(
            pokemonId = 1,
            name = "bulbasaur",
            height = 7,
            frontImageUrl = "front_url",
            backImageUrl = "back_url"
        )

        // when
        val savedFavorite = pokemonFavoriteRepository.save(favorite)
        entityManager.flush()
        entityManager.clear()

        // then
        assertNotNull(savedFavorite.id)
        val foundFavorite = pokemonFavoriteRepository.findById(savedFavorite.id).orElse(null)
        assertNotNull(foundFavorite)
        assertEquals("bulbasaur", foundFavorite.name)
        assertEquals(1, foundFavorite.pokemonId)
        assertEquals(7, foundFavorite.height)
        assertEquals("front_url", foundFavorite.frontImageUrl)
        assertEquals("back_url", foundFavorite.backImageUrl)
    }

    @Test
    fun `즐겨찾기 목록 조회 성공`() {
        // given
        val favorite1 = PokemonFavorite(
            pokemonId = 1,
            name = "bulbasaur",
            height = 7,
            frontImageUrl = "front_url",
            backImageUrl = "back_url"
        )
        val favorite2 = PokemonFavorite(
            pokemonId = 2,
            name = "ivysaur",
            height = 10,
            frontImageUrl = "front_url2",
            backImageUrl = "back_url2"
        )

        pokemonFavoriteRepository.save(favorite1)
        pokemonFavoriteRepository.save(favorite2)
        entityManager.flush()
        entityManager.clear()

        // when
        val favorites = pokemonFavoriteRepository.findAll()

        // then
        assertEquals(2, favorites.size)
        assertEquals("bulbasaur", favorites[0].name)
        assertEquals("ivysaur", favorites[1].name)
    }
} 