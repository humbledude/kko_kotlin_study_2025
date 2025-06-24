package com.joshua.feed.auth.service

import com.joshua.feed.auth.dto.AuthResponse
import com.joshua.feed.auth.dto.LoginRequest
import com.joshua.feed.auth.dto.SignupRequest
import com.joshua.feed.domain.user.UserEntity
import com.joshua.feed.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository
) {
    @Transactional
    fun signup(request: SignupRequest): AuthResponse {
        // 중복 체크
        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("이미 존재하는 사용자명입니다.")
        }
        
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }

        val user = UserEntity(
            username = request.username,
            password = request.password, // 실제로는 암호화 필요
            email = request.email
        )

        val savedUser = userRepository.save(user)
        return AuthResponse(
            userId = savedUser.id,
            username = savedUser.username,
            email = savedUser.email
        )
    }

    @Transactional(readOnly = true)
    fun login(request: LoginRequest): AuthResponse {
        val user = userRepository.findByUsername(request.username)
            ?: throw IllegalArgumentException("존재하지 않는 사용자입니다.")

        if (user.password != request.password) { // 실제로는 암호화된 비밀번호 비교 필요
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }

        return AuthResponse(
            userId = user.id,
            username = user.username,
            email = user.email
        )
    }
} 