package com.axel.todo.model

import jakarta.validation.constraints.NotEmpty

data class LoginModel(
    @field:NotEmpty(message = "username is required") val username:String,
    @field:NotEmpty(message = "password is required") val password:String
)
