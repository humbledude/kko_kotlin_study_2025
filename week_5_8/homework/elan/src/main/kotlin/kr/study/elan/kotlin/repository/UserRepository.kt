package kr.study.elan.kotlin.repository

import kr.study.elan.kotlin.domain.User
import kr.study.elan.kotlin.exception.UserNotFoundException
import org.springframework.stereotype.Repository

@Repository
class UserRepository {
    private val users: MutableCollection<User> = mutableListOf()

    fun save(user: User) {
        users.add(user)
    }

    fun findById(id: String): User? {
        val user = users.find { it.id == id }
        return user
    }

    fun findAll(size: Int): Collection<User> {
        return users.take(size)
    }
}