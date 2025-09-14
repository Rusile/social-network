package ru.rusile.socialnetwork.dao

import ru.rusile.socialnetwork.model.UserCreds
import java.util.UUID

interface UserCredsDao {

    fun insert(userCreds: UserCreds)

    fun getById(userId: UUID): UserCreds?
}