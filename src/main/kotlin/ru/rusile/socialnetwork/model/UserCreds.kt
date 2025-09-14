package ru.rusile.socialnetwork.model

import java.util.UUID

data class UserCreds(
    val userId: UUID,
    val passwordHash: String
)