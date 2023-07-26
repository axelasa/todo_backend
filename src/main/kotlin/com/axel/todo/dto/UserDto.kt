package com.axel.todo.dto

import com.axel.todo.entities.UserEntity
import java.util.Date

data class UserDto (
    val id:Long,
    val firstname:String,
    val surname:String,
    val username:String,
    val password:String,
    val createdAt:Date,
    val updatedAt:Date,
    val todos:Set<TodoDto>
) {
    companion object {
        fun fromUserEntity(s: UserEntity): UserDto {
            val data = s.todo.map {TodoDto.fromTodoEntity(it)}.toSet()
            return  UserDto(s.id!!,s.firstname,s.surname,s.username,s.password,s.createAt,s.updateAt,data)

        }
    }
}