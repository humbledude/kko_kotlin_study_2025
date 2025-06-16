package kr.study.elan.kotlin

import io.mockk.every
import io.mockk.mockk
import kr.study.elan.kotlin.exception.UserNotFoundException
import kr.study.elan.kotlin.repository.UserRepository
import kr.study.elan.kotlin.service.UserService
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class UserServiceTest {

    private val userRepository = mockk<UserRepository>()
    private val userService = UserService(userRepository)

    @Test
    fun `사용자가 없으면 UserNotFound 예외 발생`() {

        val userId = "1234"
        every { userRepository.findById(userId) } returns null

        assertThatThrownBy { userService.findById(userId) }
            .isInstanceOf(UserNotFoundException::class.java)
            .hasMessageContaining("사용자를 찾을수 없습니다. [id=${userId}]")
    }

}