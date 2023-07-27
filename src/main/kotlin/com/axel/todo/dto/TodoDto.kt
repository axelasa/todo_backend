package com.axel.todo.dto

import com.axel.todo.entities.TodoEntity
import java.util.Date

data class TodoDto (
    val id:Long,
    val title:String,
    val notes:String,
    val createdAt:Date,
    val updatedAt:Date,
    val username:String,
){
    companion object{
        fun fromTodoEntity(s: TodoEntity):TodoDto{
            return TodoDto(s.id!!,s.title,s.notes,s.createdAt,s.updatedAt,s.user.username)
        }
    }
}