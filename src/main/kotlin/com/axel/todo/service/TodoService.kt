package com.axel.todo.service

import com.axel.todo.entities.TodoEntity
import com.axel.todo.model.TodoModel
import com.axel.todo.model.UpdateTodoModel
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*


interface TodoService {
    fun createTodo(@Valid todo:TodoModel): TodoEntity
    fun getTodosByTitle(title:String):Optional<TodoEntity>
    fun searchByTitle(title: String,pageable: Pageable):Page<TodoEntity>
    fun getTodosById(id:Long):Optional<TodoEntity>
    fun getAllTodos():List<TodoEntity>
   fun updateTodo(@Valid updateTodo:UpdateTodoModel):TodoEntity

}