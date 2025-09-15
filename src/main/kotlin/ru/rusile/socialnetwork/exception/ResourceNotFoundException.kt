package ru.rusile.socialnetwork.exception

data class ResourceNotFoundException(
    override val message: String
) : RuntimeException(message)