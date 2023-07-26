package com.axel.todo.service

import com.axel.todo.entities.TodoEntity
import com.axel.todo.exceptions.ControllerExceptionHandler
import com.axel.todo.globalService.GlobalService.Companion.userService
import com.axel.todo.model.TodoModel
import com.axel.todo.model.UpdateTodoModel
import com.axel.todo.repos.TodoRepo
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class TodoServiceImpl(private var todoRepo:TodoRepo, private val userService: UserService) : TodoService {
    override fun createTodo(todo: TodoModel): TodoEntity {
        val existingTodo = getTodosByTitle(todo.title)
        if (existingTodo.isPresent) throw ControllerExceptionHandler.conflicts("This Task Already Exists")

        val optionalUser = userService.getUser(todo.username)
        if(optionalUser.isEmpty) throw  ControllerExceptionHandler.notFound("User with username ${todo.username} not found")


        val user = optionalUser.get()

        val todos = TodoEntity.newTodo(todo, user)


        return todoRepo.save(todos)
    }

    override fun getTodosByTitle(title: String): Optional<TodoEntity> {
       return todoRepo.findByTitle(title)
    }

    override fun searchByTitle(title: String, pageable: Pageable): Page<TodoEntity> {
        return  todoRepo.findByTitleLikeIgnoreCase(title, pageable)
    }

    override fun getTodosById(id: Long): Optional<TodoEntity> {
        return todoRepo.findById(id)
    }

    override fun getAllTodos(): List<TodoEntity> {
        return todoRepo.findAll()
    }


    override fun updateTodo(updateTodo: UpdateTodoModel): TodoEntity {
        val existingTodo = updateTodo.title?.let { getTodosByTitle(it) }
        if (existingTodo != null) {
            if (existingTodo.isEmpty)throw  ControllerExceptionHandler.notFound("This Task Does Not Exist")
        }

        val updateExistingTodo = existingTodo?.get()

        if (updateTodo.title != null) updateExistingTodo!!.title = updateTodo.title
        if (updateTodo.notes != null) updateExistingTodo!!.notes = updateTodo.notes


        updateExistingTodo!!.updatedAt = Date()

        return todoRepo.save(updateExistingTodo)
    }
}