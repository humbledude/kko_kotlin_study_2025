package com.joshua.feed.domain.interaction

import com.joshua.feed.domain.content.Content
import com.joshua.feed.domain.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_interactions")
class UserInteraction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    val content: Content,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val interactionType: InteractionType,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)

enum class InteractionType {
    READ,
    LIKE,
    DISLIKE
} 