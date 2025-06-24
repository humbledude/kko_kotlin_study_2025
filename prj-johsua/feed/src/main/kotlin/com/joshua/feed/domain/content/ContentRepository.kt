package com.joshua.feed.domain.content

import org.springframework.data.jpa.repository.JpaRepository

interface ContentRepository : JpaRepository<ContentEntity, Long> {
    // 필요시 커스텀 쿼리 추가
} 