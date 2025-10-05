package ru.rusile.socialnetwork.service.impl

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PasswordEncrypterServiceImplTest {

    private val service = PasswordEncrypterServiceImpl()

    @Test
    fun `hash and verify correct password`() {
        val password = "correct-horse-battery-staple"
        val stored = service.hashPassword(password)

        // базовая валидация формата
        assertNotNull(stored)
        assertTrue(stored.contains(":"), "stored string must contain ':' separators")
        val parts = stored.split(":", limit = 3)
        assertEquals(3, parts.size, "stored format must be iterations:salt:hash")
        assertTrue(parts[0].toIntOrNull() != null, "iterations part should be integer")

        // успешная верификация корректного пароля
        val ok = service.verifyPassword(stored, password)
        assertTrue(ok, "verifyPassword should return true for correct password")
    }

    @Test
    fun `verify returns false for wrong password`() {
        val password = "correct-horse-battery-staple"
        val wrong = "correct-horse-battery-staple-x"
        val stored = service.hashPassword(password)

        val result = service.verifyPassword(stored, wrong)
        assertFalse(result, "verifyPassword should return false for incorrect password")
    }

    @Test
    fun `verify returns false for malformed stored string`() {
        assertFalse(service.verifyPassword("", "pw"))
        assertFalse(service.verifyPassword("onlyhashpart", "pw"))
        assertFalse(service.verifyPassword("10000:bad-base64:also-bad-base64", "pw"))
    }
}
