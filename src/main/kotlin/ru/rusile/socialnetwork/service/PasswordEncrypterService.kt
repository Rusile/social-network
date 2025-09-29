package ru.rusile.socialnetwork.service

interface PasswordEncrypterService {

    fun hashPassword(password: String): String

    fun verifyPassword(storedHash: String, inputPassword: String): Boolean
}