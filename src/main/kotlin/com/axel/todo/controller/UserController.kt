package com.axel.todo.controller

import com.axel.todo.dto.UserDto
import com.axel.todo.globalService.GlobalService.Companion.userService
import com.axel.todo.model.ApiResponse
import com.axel.todo.model.UpdateUserModel
import com.axel.todo.model.UserModel
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController {
    @GetMapping("username")
    fun getUser(@RequestParam("username")username:String):ResponseEntity<Any>{
        val optionalUser = userService.getUser(username)
        val response = ApiResponse(HttpStatus.NOT_FOUND.value(),"User $username not found",null)
        if(optionalUser.isEmpty){
            return ResponseEntity(response,HttpStatus.NOT_FOUND)
        }
        val result = ApiResponse(HttpStatus.OK.value(),"User $username found",UserDto.fromUserEntity(optionalUser.get()))
        return ResponseEntity(result,HttpStatus.OK)
    }
    @GetMapping
    fun getAllUsers():List<UserDto> = userService.getAllUsers().stream().map {
        UserDto.fromUserEntity(it)
    }.toList()

    @PostMapping("new_user")
    fun createNewUser(@Valid @RequestBody userModel: UserModel):ResponseEntity<Any>{
        val newUser = userService.createUser(userModel)
        val result = ApiResponse(HttpStatus.CREATED.value(),"Success New User Created",UserDto.fromUserEntity(newUser))
        return ResponseEntity(result,HttpStatus.CREATED)
    }
    @PutMapping("update")
    fun updateUser(@Valid @RequestBody userModel: UpdateUserModel):ResponseEntity<Any>{
        val updatedUser = userService.updateUser(userModel)
        val result = ApiResponse(HttpStatus.CREATED.value(),"Success New User Created",UserDto.fromUserEntity(updatedUser))
        return ResponseEntity(result,HttpStatus.CREATED)
    }
}
