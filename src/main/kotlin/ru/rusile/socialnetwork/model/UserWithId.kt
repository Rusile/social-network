package ru.rusile.socialnetwork.model

import java.time.LocalDate
import java.util.UUID

data class UserWithId(
    val id: UUID,
    val user: User,
) {
    val firstName: String get() = user.firstName
    val secondName: String get() = user.secondName
    val birthdate: LocalDate get() = user.birthdate
    val biography: String? get() = user.biography
    val city: String get() = user.city
}
