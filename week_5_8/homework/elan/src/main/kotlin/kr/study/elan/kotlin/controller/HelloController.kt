package kr.study.elan.kotlin.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
class HelloController {
    @GetMapping("/world")
    fun hello(): Any {
        return mapOf(
            "hello" to "world",
        )
    }
}