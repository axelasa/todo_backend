package com.axel.todo.controller

import com.axel.todo.globalService.GlobalService.Companion.loginService
import com.axel.todo.model.ApiResponse
import com.axel.todo.model.LoginModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("login")
class LoginController {
    @PostMapping
    fun loginUser( @RequestBody loginModel: LoginModel):ResponseEntity<Any>{
        val user = loginService.loginUser(loginModel.username,loginModel.password)
        val response = ApiResponse(HttpStatus.CONFLICT.value(),"username and password is required",null)
        if (user.username.isEmpty() || user.password.isEmpty()){
            return ResponseEntity(response,HttpStatus.CONFLICT)
        }
        val result = ApiResponse(HttpStatus.OK.value(),"Successful",user)
        return ResponseEntity(result,HttpStatus.OK)
    }
}