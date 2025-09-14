package ru.rusile.socialnetwork.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rusile.socialnetwork.dto.LoginUserRequest
import ru.rusile.socialnetwork.dto.LoginUserResponse
import ru.rusile.socialnetwork.dto.RegisterUserRequest
import ru.rusile.socialnetwork.dto.RegisterUserResponse
import ru.rusile.socialnetwork.dto.UserResponse
import ru.rusile.socialnetwork.service.UserService
import java.util.UUID

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun registerUser(@RequestBody request: RegisterUserRequest): ResponseEntity<RegisterUserResponse> {
        val userId = userService.register(request.toUserModel(), request.password)

        return ResponseEntity.ok(
            RegisterUserResponse(
                userId = "$userId"
            )
        )
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody request: LoginUserRequest): ResponseEntity<LoginUserResponse> {
        val token = userService.login(request.id, request.password)

        return ResponseEntity.ok(
            LoginUserResponse(
                token = token
            )
        )
    }

    @GetMapping("/user/get/{id}")
    fun getUser(@PathVariable id: UUID): ResponseEntity<UserResponse> {
        val user = userService.getByUserId(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(
            UserResponse.from(user)
        )
    }
}