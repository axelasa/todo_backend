package com.axel.todo.model

import jakarta.validation.constraints.NotEmpty

data class UpdateTodoModel(
    @field:NotEmpty(message = "title is Required") val title:String?,
    @field:NotEmpty(message = "note is Required") val notes: String?,
    @field:NotEmpty(message = "user is required") val username: String,
)
