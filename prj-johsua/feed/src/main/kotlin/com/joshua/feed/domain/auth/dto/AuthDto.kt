package com.joshua.feed.domain.auth.dto

data class LoginRequest(
    val username: String,
    val password: String
)

data class SignupRequest(
    val username: String,
    val password: String,
    val email: String
)

data class AuthResponse(
    val userId: Long,
    val username: String,
    val email: String
) 