package com.example.server.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException::class)
    fun npeHandler(e: NullPointerException): ResponseEntity<String> {
        return ResponseEntity("NPE", HttpStatus.BAD_REQUEST)
    }
}