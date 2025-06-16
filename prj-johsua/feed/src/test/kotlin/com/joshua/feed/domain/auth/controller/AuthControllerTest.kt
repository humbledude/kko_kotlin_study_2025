package com.joshua.feed.domain.auth.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.joshua.feed.domain.auth.dto.AuthResponse
import com.joshua.feed.domain.auth.dto.LoginRequest
import com.joshua.feed.domain.auth.dto.SignupRequest
import com.joshua.feed.domain.auth.service.AuthService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

class AuthControllerTest {

    private lateinit var webTestClient: WebTestClient
    private lateinit var objectMapper: ObjectMapper

    @Mock
    private lateinit var authService: AuthService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        objectMapper = ObjectMapper()
        webTestClient = WebTestClient
            .bindToController(AuthController(authService))
            .build()
    }

    @Test
    fun `회원가입 성공 테스트`() {
        // given
        val signupRequest = SignupRequest(
            username = "testuser",
            password = "password123",
            email = "test@example.com"
        )

        val authResponse = AuthResponse(
            userId = 1L,
            username = signupRequest.username,
            email = signupRequest.email
        )

        `when`(authService.signup(signupRequest)).thenReturn(authResponse)

        // when & then
        webTestClient.post()
            .uri("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(objectMapper.writeValueAsString(signupRequest))
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.userId").isEqualTo(1)
            .jsonPath("$.username").isEqualTo(signupRequest.username)
            .jsonPath("$.email").isEqualTo(signupRequest.email)

        verify(authService).signup(signupRequest)
    }

    @Test
    fun `로그인 성공 테스트`() {
        // given
        val loginRequest = LoginRequest(
            username = "testuser",
            password = "password123"
        )

        val authResponse = AuthResponse(
            userId = 1L,
            username = loginRequest.username,
            email = "test@example.com"
        )

        `when`(authService.login(loginRequest)).thenReturn(authResponse)

        // when & then
        webTestClient.post()
            .uri("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(objectMapper.writeValueAsString(loginRequest))
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.userId").isEqualTo(1)
            .jsonPath("$.username").isEqualTo(loginRequest.username)
            .jsonPath("$.email").isEqualTo("test@example.com")

        verify(authService).login(loginRequest)
    }

    @Test
    fun `잘못된 요청 형식으로 회원가입 실패 테스트`() {
        // given
        val invalidRequest = """
            {
                "username": "testuser",
                "password": "password123"
            }
        """.trimIndent()

        // when & then
        webTestClient.post()
            .uri("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalidRequest)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `잘못된 요청 형식으로 로그인 실패 테스트`() {
        // given
        val invalidRequest = """
            {
                "username": "testuser"
            }
        """.trimIndent()

        // when & then
        webTestClient.post()
            .uri("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalidRequest)
            .exchange()
            .expectStatus().isBadRequest
    }
} 