package com.axel.todo.service

import com.axel.todo.entities.TodoEntity
import com.axel.todo.entities.UserEntity
import com.axel.todo.exceptions.ControllerExceptionHandler
import com.axel.todo.model.TodoModel
import com.axel.todo.model.UpdateTodoModel
import com.axel.todo.repos.TodoRepo
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class TodoServiceImpl(private var todoRepo:TodoRepo, private val userService: UserService) : TodoService {
    override fun createTodo(todo: TodoModel): TodoEntity {


        val optionalUser = userService.getUser(todo.username)
        if(optionalUser.isEmpty) throw  ControllerExceptionHandler.notFound("User with username ${todo.username} not found")
        val user = optionalUser.get()

        val existingTodo = getTodosByTitleAndUser(todo.title, user)
        if (existingTodo.isPresent) throw ControllerExceptionHandler.conflicts("This Task Already Exists with title ${todo.title}")

        val todos = TodoEntity.newTodo(todo, user)


        return todoRepo.save(todos)
    }

    override fun getTodosByTitleAndUser(title: String, user: UserEntity): Optional<TodoEntity> {
       return todoRepo.findByTitleAndUser(title, user)
    }

    override fun searchByTitle(title: String, pageable: Pageable): Page<TodoEntity> {
        return  todoRepo.findByTitleLikeIgnoreCase(title, pageable)
    }

    override fun findTodos(specification: Specification<TodoEntity>, pageable: Pageable): Page<TodoEntity> {
        return todoRepo.findAll(specification, pageable)
    }

    override fun findTodos(pageable: Pageable): Page<TodoEntity> {
        return todoRepo.findAll(pageable)
    }

    override fun getTodosById(id: Long): Optional<TodoEntity> {
        return todoRepo.findById(id)
    }

    override fun getTodosByUsername(username: String, pageable: Pageable): Page<TodoEntity> {
        return todoRepo.findByUser_Username(username, pageable)
    }

    override fun getAllTodos(): List<TodoEntity> {
        return todoRepo.findAll()
    }


    override fun updateTodo(updateTodo: UpdateTodoModel): TodoEntity {
        val existingTodo =  getTodosById(updateTodo.id)
        if (existingTodo.isEmpty)throw  ControllerExceptionHandler.notFound("This Task Does Not Exist")

        val updateExistingTodo = existingTodo.get()

        if (updateTodo.title != null) updateExistingTodo.title = updateTodo.title
        if (updateTodo.notes != null) updateExistingTodo.notes = updateTodo.notes

        updateExistingTodo.updatedAt = Date()

        return todoRepo.save(updateExistingTodo)
    }
}