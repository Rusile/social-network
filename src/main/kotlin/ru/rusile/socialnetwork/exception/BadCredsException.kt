package ru.rusile.socialnetwork.exception

data class BadCredsException(
    override val message: String
) : RuntimeException(message)