package ru.rusile.socialnetwork.service.impl

import org.springframework.stereotype.Service
import ru.rusile.socialnetwork.service.PasswordEncrypterService
import java.security.MessageDigest
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import java.security.SecureRandom
import java.util.Base64

@Service
class PasswordEncrypterServiceImpl : PasswordEncrypterService {

    /**
     * Формат возвращаемой строки: iterations:saltB64:hashB64
     */
    override fun hashPassword(password: String): String {
        val salt = ByteArray(SALT_LENGTH_IN_BITS)
        RANDOM.nextBytes(salt)

        val spec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS_COUNT, KEY_LENGTH_IN_BITS)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = factory.generateSecret(spec).encoded

        val saltB64 = Base64.getEncoder().encodeToString(salt)
        val hashB64 = Base64.getEncoder().encodeToString(hash)
        return "$ITERATIONS_COUNT:$saltB64:$hashB64"
    }

    /**
     * Ожидает формат: iterations:saltB64:hashB64
     */
    override fun verifyPassword(storedHash: String, inputPassword: String): Boolean {
        val parts = storedHash.split(":", limit = 3)
        if (parts.size != 3) return false

        val iterations = parts[0].trim().toIntOrNull() ?: return false
        val salt = try {
            Base64.getDecoder().decode(parts[1].trim())
        } catch (_: IllegalArgumentException) {
            return false
        }

        val originalHash = try {
            Base64.getDecoder().decode(parts[2].trim())
        } catch (_: IllegalArgumentException) {
            return false
        }

        val spec = PBEKeySpec(inputPassword.toCharArray(), salt, iterations, originalHash.size * 8)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val newHash = factory.generateSecret(spec).encoded

        return MessageDigest.isEqual(originalHash, newHash)
    }

    companion object {
        private const val ITERATIONS_COUNT = 10_000
        private const val SALT_LENGTH_IN_BITS = 16
        private const val KEY_LENGTH_IN_BITS = 256
        private const val RANDOM = SecureRandom()
    }
}
