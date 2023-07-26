package com.axel.todo.service

import com.axel.todo.entities.TodoEntity
import com.axel.todo.entities.UserEntity
import com.axel.todo.model.UpdateUserModel
import com.axel.todo.model.UserModel
import java.util.Optional

interface UserService {
    fun createUser(user:UserModel):UserEntity
    fun getUser(username:String):Optional<UserEntity>
    fun getUserById(id: Long,):Optional<UserEntity>
    fun updateUser(user: UpdateUserModel):UserEntity
    fun getAllUsers():List<UserEntity>
}