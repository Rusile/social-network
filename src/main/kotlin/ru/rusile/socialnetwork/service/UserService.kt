package ru.rusile.socialnetwork.service

import ru.rusile.socialnetwork.model.User
import ru.rusile.socialnetwork.model.UserWithId
import java.util.*

interface UserService {

    fun register(user: User, password: String): UUID

    fun login(userId: String, password: String): String

    fun getByUserId(userId: UUID): UserWithId?
}