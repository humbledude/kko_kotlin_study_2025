package com.example.demo.sample

import com.example.demo.sample.HelloRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sample")
class SampleController {

    @GetMapping("/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getHelloWorld(@RequestParam name: String? = "Guest") : ResponseEntity<HelloResponse> {
        return ResponseEntity
            .status(200)
            .body( HelloResponse(message = "Hello", name = name!!))
    }

    @PostMapping("/hello", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun postHelloWorldForm(@RequestParam message: String): String {
        return "Hello World, ${message}"
    }

    @PostMapping("/hello", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun postHelloWorldJson(@RequestBody request: HelloRequest): String {
        return "Hello World, message = ${request.message}"
    }

}