package com.axel.todo.service

import com.axel.todo.entities.TodoEntity
import com.axel.todo.entities.UserEntity
import com.axel.todo.model.TodoModel
import com.axel.todo.model.UpdateTodoModel
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import java.util.*


interface TodoService {
    fun createTodo(@Valid todo:TodoModel): TodoEntity
    fun getTodosByTitleAndUser(title: String, user: UserEntity):Optional<TodoEntity>
    fun searchByTitle(title: String,pageable: Pageable):Page<TodoEntity>
    fun findTodos(specification: Specification<TodoEntity> , pageable: Pageable):Page<TodoEntity>
    fun findTodos(pageable: Pageable):Page<TodoEntity>
    fun getTodosById(id:Long):Optional<TodoEntity>
    fun getTodosByUsername(username: String, pageable: Pageable):Page<TodoEntity>
    fun getAllTodos():List<TodoEntity>
   fun updateTodo(@Valid updateTodo:UpdateTodoModel):TodoEntity

}