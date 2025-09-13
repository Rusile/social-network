package ru.rusile.socialnetwork.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ru.rusile.socialnetwork.jooq.tables.references.USERS

@RestController
class PingController {

    @GetMapping("/ping")
    fun ping(): String {
        return "pong"
    }
}