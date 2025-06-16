package com.joshua.feed.content

import com.joshua.feed.domain.content.Content

interface ContentProvider {
    suspend fun getContent(id: String): Content
    suspend fun getRandomContent(): Content
    suspend fun getContents(count: Int): List<Content>
} 