package kr.study.elan.kotlin.domain

data class ErrorResponse(
    val message: String,
    val timestamp: Long = System.currentTimeMillis() / 1000,
)