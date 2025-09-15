package ru.rusile.socialnetwork.dto

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.UUID

data class LoginUserRequest(

    @field:UUID(message = "Id must be a valid UUID")
    @field:NotBlank(message = "Id is required")
    val id: String? = null,

    @field:NotBlank(message = "Password is required")
    val password: String? = null,
)