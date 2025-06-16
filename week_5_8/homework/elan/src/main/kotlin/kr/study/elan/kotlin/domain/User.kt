package kr.study.elan.kotlin.domain

import java.util.UUID

data class User(
    val name: String,
    val id: String = UUID.randomUUID().toString(),
)
