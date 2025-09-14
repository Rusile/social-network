package ru.rusile.socialnetwork.dto

import ru.rusile.socialnetwork.model.User
import java.time.LocalDate

data class RegisterUserRequest(
    val firstName: String,
    val secondName: String,
    val birthdate: LocalDate,
    val biography: String?,
    val city: String,
    val password: String,
) {
    fun toUserModel() =  User(
        secondName = secondName,
        firstName = firstName,
        birthdate = birthdate,
        city = city,
        biography = biography
    )
}
