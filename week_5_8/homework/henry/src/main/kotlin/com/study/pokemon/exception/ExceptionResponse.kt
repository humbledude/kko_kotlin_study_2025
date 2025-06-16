package com.study.pokemon.exception

data class ExceptionResponse(
    val status: Int,
    val message: String,
    val path: String,
    val exception: String? = null,
)
