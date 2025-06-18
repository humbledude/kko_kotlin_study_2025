package com.joshua.feed.domain.content

interface ContentProvider {
    suspend fun getContent(id: Long): Content
    suspend fun getContents(count: Int): List<Content>
} 