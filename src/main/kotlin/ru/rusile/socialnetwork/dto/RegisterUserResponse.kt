package ru.rusile.socialnetwork.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterUserResponse(
    @field:JsonProperty("user_id")
    val userId: String,
)
