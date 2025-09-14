package ru.rusile.socialnetwork.model

import java.time.LocalDate

data class User (
    val firstName: String,
    val secondName: String,
    val birthdate: LocalDate,
    val biography: String?,
    val city: String,
)