package com.axel.todo.globalService

import com.axel.todo.service.LoginService
import com.axel.todo.service.TodoService
import com.axel.todo.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GlobalService {
    companion object{
        lateinit var todoService:TodoService
        lateinit var userService:UserService
        lateinit var loginService:LoginService
    }
    @Autowired
    fun setTodoService(todoService: TodoService){
        GlobalService.todoService = todoService
    }
    @Autowired
    fun setUserService(userService: UserService){
        GlobalService.userService =userService
    }
    @Autowired
    fun setLoginService(loginService: LoginService){
        GlobalService.loginService =loginService
    }
}