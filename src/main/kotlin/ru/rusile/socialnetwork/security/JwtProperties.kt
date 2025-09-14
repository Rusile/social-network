package ru.rusile.socialnetwork.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties @ConstructorBinding constructor(
    var secret: String,
    var expirationMs: Long
)