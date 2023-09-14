package com.axel.todo.repos

import com.axel.todo.entities.TodoEntity
import com.axel.todo.entities.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TodoRepo :JpaRepository<TodoEntity,Int>, JpaSpecificationExecutor<TodoEntity> {
    fun findByTitleAndUser(title: String, user: UserEntity):Optional<TodoEntity>
    @Query(nativeQuery = true, value = "SELECT * FROM todo where title like %:title% ")
    fun findByTitleLikeIgnoreCase(title: String,pageable: Pageable):Page<TodoEntity>

    fun findByUser_Username(username: String,pageable: Pageable):Page<TodoEntity>
    fun findById(id: Long):Optional<TodoEntity>
}