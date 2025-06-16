package kr.study.elan.kotlin.controller

import kr.study.elan.kotlin.domain.User
import kr.study.elan.kotlin.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {

    @GetMapping("")
    fun findAll(): Collection<User> {
        return userService.findAll()
    }

    @GetMapping("/{id}")
    fun find(@PathVariable id: String): User {
        return userService.findById(id)
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody createUser: User): Map<String, Any> {
        userService.add(createUser)
        return mapOf(
            "id" to createUser.id
        )
    }
}