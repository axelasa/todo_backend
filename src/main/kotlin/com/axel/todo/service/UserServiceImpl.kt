package com.axel.todo.service

import com.axel.todo.entities.UserEntity
import com.axel.todo.exceptions.ControllerExceptionHandler
import com.axel.todo.model.UpdateUserModel
import com.axel.todo.model.UserModel
import com.axel.todo.repos.UserRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class UserServiceImpl(private var userRepo: UserRepo) : UserService {
    override fun createUser(user: UserModel): UserEntity {
        val existingUser = getUser(user.username)
        if (existingUser.isPresent) throw ControllerExceptionHandler.conflicts("This User Already Exists")
        val newUser = UserEntity.createNewUser(user)
        return userRepo.save(newUser)
    }

    override fun getUser(username: String): Optional<UserEntity> {
       return userRepo.findByUsernameEqualsIgnoreCase(username)
    }

    override fun getUserById(id: Long): Optional<UserEntity> {

        return userRepo.findById(id)
    }

    override fun updateUser(user: UpdateUserModel): UserEntity {
        val existingUser = getUser(user.username)

        if(existingUser.isEmpty) throw ControllerExceptionHandler.notFound("user not found")

        val updateUser = existingUser.get()

        if (user.firstname !=null) updateUser.firstname = user.firstname
        if (user.surname !=null) updateUser.surname = user.surname
        if (user.password !=null) updateUser.password = user.password
        updateUser.updateAt = Date()
        return userRepo.save(updateUser)
    }


    override fun getAllUsers(): List<UserEntity> {
        return userRepo.findAll()
    }
}