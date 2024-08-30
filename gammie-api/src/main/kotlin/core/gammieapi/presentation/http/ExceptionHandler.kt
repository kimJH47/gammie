package core.gammieapi.presentation.http

import core.gammieapi.application.CustomException
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handle(e : CustomException) : ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .build()
    }
}