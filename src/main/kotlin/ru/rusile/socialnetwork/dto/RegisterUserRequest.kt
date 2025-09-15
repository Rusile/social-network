package ru.rusile.socialnetwork.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import ru.rusile.socialnetwork.model.User
import java.time.LocalDate

data class RegisterUserRequest(
    @field:JsonProperty("first_name")
    @field:NotBlank(message = "First name is required")
    @field:Size(min = 1, max = 255, message = "First name must be between 1 and 255 characters")
    val firstName: String? = null,

    @field:JsonProperty("second_name")
    @field:NotBlank(message = "Second name is required")
    @field:Size(min = 1, max = 255, message = "Second name must be between 1 and 255 characters")
    val secondName: String? = null,

    @field:JsonProperty("birthdate")
    @field:NotNull(message = "Birthdate is required")
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val birthdate: LocalDate? = null,

    @field:JsonProperty("biography")
    @field:Size(max = 511, message = "Biography cannot exceed 511 characters")
    val biography: String? = null,

    @field:JsonProperty("city")
    @field:NotBlank(message = "City is required")
    @field:Size(min = 1, max = 255, message = "City must be between 1 and 255 characters")
    val city: String? = null,

    @field:JsonProperty("password")
    @field:NotBlank(message = "Password is required")
    @field:Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    val password: String? = null,
) {
    fun toUserModel() = User(
        secondName = secondName!!,
        firstName = firstName!!,
        birthdate = birthdate!!,
        city = city!!,
        biography = biography
    )
}