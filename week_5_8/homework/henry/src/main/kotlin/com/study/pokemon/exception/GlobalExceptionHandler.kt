package com.study.pokemon.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.server.ServerWebExchange

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleException(
        exchange: ServerWebExchange,
        ex: CustomException
    ): ResponseEntity<ExceptionResponse> =
        ResponseEntity
            .status(ex.status)
            .body(
                ExceptionResponse(
                    status = ex.status.value(),
                    message = ex.message,
                    path = exchange.request.path.toString(),
                    exception = ex.javaClass.name,
                ),
            )
}
