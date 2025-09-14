package ru.rusile.socialnetwork.dao

import ru.rusile.socialnetwork.model.UserWithId
import java.util.*

interface UserDao {

    fun insert(user: UserWithId)

    fun getById(id: UUID): UserWithId?
}