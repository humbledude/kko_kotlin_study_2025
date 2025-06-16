package kr.study.elan.kotlin.controller

import kr.study.elan.kotlin.domain.ErrorResponse
import kr.study.elan.kotlin.exception.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUserNotFoundException(e: UserNotFoundException): ErrorResponse {
        return ErrorResponse(e.message ?: e.localizedMessage)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ErrorResponse {
        return ErrorResponse(e.message)
    }

}