package com.axel.todo.repos

import com.axel.todo.entities.TodoEntity
import com.axel.todo.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepo: JpaRepository<UserEntity,Long> {
    fun findByUsernameEqualsIgnoreCase(username:String):Optional<UserEntity>
    override fun findById(id: Long):Optional<UserEntity>
}