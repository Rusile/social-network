package ru.rusile.socialnetwork.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.rusile.socialnetwork.exception.BadCredsException

@Component
class JwtFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization") ?: ""
        if (header.startsWith("Bearer ")) {
            try {
                val token = header.removePrefix("Bearer ").trim()
                val userId = jwtUtil.validateAndGetUserId(token)
                val auth = UsernamePasswordAuthenticationToken(userId, null, emptyList())
                SecurityContextHolder.getContext().authentication = auth
            } catch (e: Exception) {
                throw BadCredsException(e.message ?: "Can not parse token")
            }
        }
        filterChain.doFilter(request, response)
    }
}
