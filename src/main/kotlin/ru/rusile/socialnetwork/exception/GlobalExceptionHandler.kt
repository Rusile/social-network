package ru.rusile.socialnetwork.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.rusile.socialnetwork.dto.ErrorResponse

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorMessage = ex.bindingResult.fieldErrors
            .joinToString(separator = ",") { error ->
                "${error.field}: ${error.defaultMessage ?: "Invalid value"}"
            }

        val response = ErrorResponse(
            code = HttpStatus.BAD_REQUEST.value(),
            message = errorMessage
        )

        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handlerNotFound(ex: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = HttpStatus.NOT_FOUND.value(),
            message = ex.message
        )

        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadCredsException::class)
    fun handlerBadCreds(ex: BadCredsException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = HttpStatus.FORBIDDEN.value(),
            message = ex.message
        )

        return ResponseEntity(response, HttpStatus.FORBIDDEN)
    }
}