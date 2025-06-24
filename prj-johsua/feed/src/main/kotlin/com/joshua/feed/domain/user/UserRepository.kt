package com.joshua.feed.domain.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
    fun existsByUsername(username: String): Boolean
    fun findByEmail(email: String): UserEntity?
    fun existsByEmail(email: String): Boolean
} 