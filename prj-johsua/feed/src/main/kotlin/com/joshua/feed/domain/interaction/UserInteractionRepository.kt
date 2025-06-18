package com.joshua.feed.domain.interaction

import org.springframework.data.jpa.repository.JpaRepository

interface UserInteractionRepository : JpaRepository<UserInteractionEntity, Long> {
    // 필요시 커스텀 쿼리 추가
} 