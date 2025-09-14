package ru.rusile.socialnetwork.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ru.rusile.socialnetwork.model.UserWithId
import java.time.LocalDate
import java.util.UUID

data class UserResponse(
    val id: UUID,
    @field:JsonProperty("first_name")
    val firstName: String,
    @field:JsonProperty("second_name")
    val secondName: String,
    val birthdate: LocalDate,
    val biography: String?,
    val city: String
) {

    companion object {
        fun from(user: UserWithId) = UserResponse(
            id = user.id,
            firstName = user.firstName,
            secondName = user.secondName,
            birthdate = user.birthdate,
            biography = user.biography,
            city = user.city,
        )
    }
}