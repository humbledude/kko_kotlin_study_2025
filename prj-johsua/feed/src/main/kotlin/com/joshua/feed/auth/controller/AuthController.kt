package com.joshua.feed.auth.controller

import com.joshua.feed.auth.dto.AuthResponse
import com.joshua.feed.auth.dto.LoginRequest
import com.joshua.feed.auth.dto.SignupRequest
import com.joshua.feed.auth.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/signup")
    fun signup(@RequestBody request: SignupRequest): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.signup(request))
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.login(request))
    }
} 