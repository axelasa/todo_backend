package com.axel.todo.model

import jakarta.validation.constraints.NotNull

data class UpdateTodoModel(
     @field:NotNull(message = "note id is Required") val id: Long,
     val title:String?,
     val notes: String?,
)
