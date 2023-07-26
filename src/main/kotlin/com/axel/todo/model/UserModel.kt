package com.axel.todo.model

import jakarta.validation.constraints.NotEmpty

data class UserModel (
    @field:NotEmpty(message = "first name is required")  val firstname:String,
    @field:NotEmpty(message = "Surname is required") val surname:String,
    @field:NotEmpty(message = "Username is Required") val username:String,
    @field:NotEmpty(message = "Password is required") val password:String
    )