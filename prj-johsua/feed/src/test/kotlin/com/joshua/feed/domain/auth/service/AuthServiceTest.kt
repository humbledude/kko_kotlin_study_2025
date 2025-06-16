package com.joshua.feed.domain.auth.service

import com.joshua.feed.domain.auth.dto.LoginRequest
import com.joshua.feed.domain.auth.dto.SignupRequest
import com.joshua.feed.domain.user.User
import com.joshua.feed.domain.user.UserRepository
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AuthServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var authService: AuthService

    @BeforeEach
    fun setup() {
        userRepository = mockk(relaxed = true)
        authService = AuthService(userRepository)
        clearAllMocks()
    }

    @Test
    fun `회원가입 성공 테스트`() {
        // given
        val signupRequest = SignupRequest(
            username = "testuser",
            password = "password123",
            email = "test@example.com"
        )
        val savedUser = User(
            id = 1L,
            username = signupRequest.username,
            password = signupRequest.password,
            email = signupRequest.email
        )
        every { userRepository.existsByUsername(signupRequest.username) } returns false
        every { userRepository.save(any()) } returns savedUser

        // when
        val result = authService.signup(signupRequest)

        // then
        assert(result.userId == 1L)
        assert(result.username == signupRequest.username)
        assert(result.email == signupRequest.email)
        verify { userRepository.existsByUsername(signupRequest.username) }
        verify { userRepository.save(any()) }
    }

    @Test
    fun `중복된 사용자명으로 회원가입 실패 테스트`() {
        // given
        val signupRequest = SignupRequest(
            username = "testuser",
            password = "password123",
            email = "test@example.com"
        )
        every { userRepository.existsByUsername(signupRequest.username) } returns true

        // when & then
        assertThrows<IllegalArgumentException> {
            authService.signup(signupRequest)
        }
    }

    @Test
    fun `로그인 성공 테스트`() {
        // given
        val loginRequest = LoginRequest(
            username = "testuser",
            password = "password123"
        )
        val user = User(
            id = 1L,
            username = loginRequest.username,
            password = loginRequest.password,
            email = "test@example.com"
        )
        every { userRepository.findByUsername(loginRequest.username) } returns user

        // when
        val result = authService.login(loginRequest)

        // then
        assert(result.userId == 1L)
        assert(result.username == loginRequest.username)
        assert(result.email == "test@example.com")
    }

    @Test
    fun `존재하지 않는 사용자로 로그인 실패 테스트`() {
        // given
        val loginRequest = LoginRequest(
            username = "nonexistent",
            password = "password123"
        )
        every { userRepository.findByUsername(loginRequest.username) } returns null

        // when & then
        assertThrows<IllegalArgumentException> {
            authService.login(loginRequest)
        }
    }

    @Test
    fun `잘못된 비밀번호로 로그인 실패 테스트`() {
        // given
        val loginRequest = LoginRequest(
            username = "testuser",
            password = "wrongpassword"
        )
        val user = User(
            id = 1L,
            username = loginRequest.username,
            password = "correctpassword",
            email = "test@example.com"
        )
        every { userRepository.findByUsername(loginRequest.username) } returns user

        // when & then
        assertThrows<IllegalArgumentException> {
            authService.login(loginRequest)
        }
    }
} 