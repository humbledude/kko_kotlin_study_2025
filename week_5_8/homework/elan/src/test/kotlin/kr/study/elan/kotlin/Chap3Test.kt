package kr.study.elan.kotlin

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import kr.study.elan.kotlin.domain.User
import kr.study.elan.kotlin.repository.UserRepository
import kr.study.elan.kotlin.service.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Chap3Test {

    @MockK
    private lateinit var userRepository: UserRepository

    @InjectMockKs
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        // MockK 애노테이션 초기화
        MockKAnnotations.init(this)
    }

    @Test
    fun `사용자 추가`() {
        // given
        val testUser = User(name = "test1")
        every { userRepository.save(any()) } just runs

        // when
        userService.add(testUser)

        // then
        verify { userRepository.save(any()) }
    }

}