package com.axel.todo.service

import com.axel.todo.entities.UserEntity

interface LoginService {
    fun loginUser(username:String,password:String): UserEntity
}