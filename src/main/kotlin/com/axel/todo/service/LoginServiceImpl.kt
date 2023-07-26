package com.axel.todo.service

import com.axel.todo.entities.UserEntity
import com.axel.todo.exceptions.ControllerExceptionHandler
import org.springframework.stereotype.Service

@Service
class LoginServiceImpl(private val userService:UserService) : LoginService {
    override fun loginUser(username: String, password: String): UserEntity {
        val user = userService.getUser(username)
        if (user.isEmpty){
            throw ControllerExceptionHandler.unAuthorized("wrong username")
        }
        if(user.get().password != password){
            throw ControllerExceptionHandler.unAuthorized("wrong credentials or something")
        }
        return  user.get()
    }
}