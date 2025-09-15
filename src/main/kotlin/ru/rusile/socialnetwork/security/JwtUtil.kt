package ru.rusile.socialnetwork.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey

@Component
class JwtUtil(jwtProps: JwtProperties) {
    private val key: SecretKey = Keys.hmacShaKeyFor(jwtProps.secret.toByteArray())
    private val expirationMs: Long = jwtProps.expirationMs

    fun generateToken(userId: UUID): String {
        val now = Date()
        val exp = Date(now.time + expirationMs)
        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(exp)
            .signWith(key, Jwts.SIG.HS256)
            .compact()
    }

    fun validateAndGetUserId(token: String): UUID {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

        return UUID.fromString(claims.subject)
    }
}