package com.joshua.feed.content

interface Content {
    val id: String
    val title: String
    val description: String
    val imageUrl: String?
    val metadata: Map<String, Any>
} 