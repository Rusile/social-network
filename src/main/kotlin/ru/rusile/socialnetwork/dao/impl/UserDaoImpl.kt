package ru.rusile.socialnetwork.dao.impl

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import ru.rusile.socialnetwork.dao.UserDao
import ru.rusile.socialnetwork.jooq.tables.references.USERS
import ru.rusile.socialnetwork.model.User
import ru.rusile.socialnetwork.model.UserWithId
import java.util.UUID

@Repository
class UserDaoImpl(
    private val dsl: DSLContext
) : UserDao {

    override fun insert(user: UserWithId) {
        dsl.insertInto(USERS)
            .set(USERS.ID, user.id)
            .set(USERS.SURNAME, user.secondName)
            .set(USERS.NAME, user.firstName)
            .set(USERS.BIRTH_DATE, user.birthdate)
            .set(USERS.CITY, user.city)
            .set(USERS.BIOGRAPHY, user.biography)
            .execute()
    }

    override fun getById(
        id: UUID
    ) = dsl.selectFrom(USERS)
        .where(USERS.ID.eq(id))
        .fetchOne { record ->
            UserWithId(
                id = record[USERS.ID]!!,
                user = User(
                    secondName = record.surname!!,
                    firstName = record.name!!,
                    birthdate = record.birthDate!!,
                    city = record.city!!,
                    biography = record.biography
                ),
            )
        }

    override fun searchUsers(
        firstName: String,
        lastName: String
    ) = dsl.selectFrom(USERS)
        .where(
            USERS.NAME.like("%${firstName.lowercase()}%")
                .and(USERS.SURNAME.like("%${lastName.lowercase()}%"))
        ).fetch { record ->
            UserWithId(
                id = record[USERS.ID]!!,
                user = User(
                    secondName = record.surname!!,
                    firstName = record.name!!,
                    birthdate = record.birthDate!!,
                    city = record.city!!,
                    biography = record.biography
                ),
            )
        }
}