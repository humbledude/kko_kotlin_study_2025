package com.example.demo.sample

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DataJpaTest
class UserRepositoryTest @Autowired constructor (
    val userRepository: UserRepository
){

    @Test
    fun `save and find by id`() {
        // given
        val user = User(name = "테스터", email = "tester@example.com")

        // when
        val saved = userRepository.save(user)
        val found = userRepository.findById(saved.id!!).orElseThrow()

        // then
        assertEquals(saved.id, found.id)
        assertEquals("테스터", found.name)
        assertEquals("tester@example.com", found.email)
    }

    @Test
    fun `find by email custom query`() {
        // given
        val email = "custom@example.com"
        userRepository.save(User(name = "Foo", email = email))

        // when
        val found = userRepository.findByEmail(email)

        // then
        assertNotNull(found)
        assertEquals("Foo", found!!.name)
    }


}