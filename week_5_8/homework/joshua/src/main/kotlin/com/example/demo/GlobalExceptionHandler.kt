package com.example.demo

import com.example.demo.pokemon.model.PokemonNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ArithmeticException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleArithmeticException(ex: ArithmeticException):  SimpleResponse {
        return SimpleResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "error",
        )
    }

    @ExceptionHandler(PokemonNotFoundException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handlePokemonNotFoundException(ex: PokemonNotFoundException): SimpleResponse {
        return SimpleResponse (
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "PokemonNotFoundException"
        )
    }
}