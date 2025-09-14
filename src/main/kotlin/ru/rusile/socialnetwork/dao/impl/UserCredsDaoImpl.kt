package ru.rusile.socialnetwork.dao.impl

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import ru.rusile.socialnetwork.dao.UserCredsDao
import ru.rusile.socialnetwork.jooq.tables.UserCreds.Companion.USER_CREDS
import ru.rusile.socialnetwork.model.UserCreds
import java.util.UUID

@Repository
class UserCredsDaoImpl(
    private val dsl: DSLContext
) : UserCredsDao {

    override fun insert(userCreds: UserCreds) {
        dsl.insertInto(USER_CREDS)
            .set(USER_CREDS.USER_ID, userCreds.userId)
            .set(USER_CREDS.PASSWORD_HASH, userCreds.passwordHash)
            .execute()
    }

    override fun getById(userId: UUID): UserCreds? {
        return dsl.selectFrom(USER_CREDS)
            .where(USER_CREDS.USER_ID.eq(userId))
            .fetchOne()
            ?.let { record ->
                UserCreds(
                    userId = record.userId!!,
                    passwordHash = record.passwordHash!!
                )
            }
    }
}
