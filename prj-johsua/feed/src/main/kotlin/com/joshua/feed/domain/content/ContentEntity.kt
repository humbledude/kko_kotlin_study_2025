package com.joshua.feed.domain.content

import com.fasterxml.jackson.databind.JsonNode
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime

@Entity
@Table(name = "contents")
class ContentEntity(
    @Id
    override val id: Long = 0,

    @Column(nullable = false)
    override val title: String,

    @Column(nullable = false, length = 1000)
    override val description: String,

    @Column(name = "image_url")
    override val imageUrl: String? = null,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "body", columnDefinition = "TEXT", nullable = false)
    override val body: JsonNode,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
) : Content