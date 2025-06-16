package kr.study.elan.kotlin.service

import kr.study.elan.kotlin.domain.User
import kr.study.elan.kotlin.exception.UserNotFoundException
import kr.study.elan.kotlin.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun add(createUser: User) {
        userRepository.save(createUser)
    }

    fun findById(id: String): User {
        val user = userRepository.findById(id)
        if (user == null) {
            throw UserNotFoundException("사용자를 찾을수 없습니다. [id=${id}]")
        }
        return user
    }

    fun findAll(size: Int = 15): Collection<User> {
        return userRepository.findAll(size)
    }
}