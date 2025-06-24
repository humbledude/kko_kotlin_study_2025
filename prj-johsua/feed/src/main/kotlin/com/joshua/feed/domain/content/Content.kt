package com.joshua.feed.domain.content

import com.fasterxml.jackson.databind.JsonNode

interface Content {
    val id: Long
    val title: String
    val description: String
    val imageUrl: String?
    val body: JsonNode
} 