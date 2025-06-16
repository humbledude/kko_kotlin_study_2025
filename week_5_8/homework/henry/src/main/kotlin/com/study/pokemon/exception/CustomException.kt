package com.study.pokemon.exception

import org.springframework.http.HttpStatus

data class CustomException(
    override val message: String,
    val status: HttpStatus,
) : RuntimeException(message)
