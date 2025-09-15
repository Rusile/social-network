package ru.rusile.socialnetwork.service.impl

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.rusile.socialnetwork.dao.UserCredsDao
import ru.rusile.socialnetwork.dao.UserDao
import ru.rusile.socialnetwork.exception.BadCredsException
import ru.rusile.socialnetwork.exception.ResourceNotFoundException
import ru.rusile.socialnetwork.model.User
import ru.rusile.socialnetwork.model.UserCreds
import ru.rusile.socialnetwork.model.UserWithId
import ru.rusile.socialnetwork.security.JwtUtil
import ru.rusile.socialnetwork.service.UserService
import java.util.UUID

@Service
class UserServiceImpl(
    private val userDao: UserDao,
    private val userCredsDao: UserCredsDao,
    private val jwtUtil: JwtUtil,
) : UserService {

    private val encoder = BCryptPasswordEncoder()

    @Transactional
    override fun register(user: User, password: String): UUID {
        val userId = UUID.randomUUID()
        userDao.insert(
            UserWithId(
                id = userId,
                user = user
            )
        )

        val hash = encoder.encode(password)
        userCredsDao.insert(
            UserCreds(
                userId = userId,
                passwordHash = hash
            )
        )

        return userId
    }

    override fun login(userId: String, password: String): String {
        val userCreds = userCredsDao.getById(UUID.fromString(userId))
            ?: throw ResourceNotFoundException("User not found")

        if (!encoder.matches(password, userCreds.passwordHash)) {
            throw BadCredsException("Bad credentials")
        }
        return jwtUtil.generateToken(userCreds.userId)
    }

    override fun getByUserId(userId: UUID) = userDao.getById(userId)
}