package com.axel.todo.controller

import com.axel.todo.dto.TodoDto
import com.axel.todo.dto.UserDto
import com.axel.todo.entities.TodoEntity
import com.axel.todo.globalService.GlobalService.Companion.todoService
import com.axel.todo.globalService.GlobalService.Companion.userService
import com.axel.todo.model.ApiResponse
import com.axel.todo.model.TodoModel
import com.axel.todo.model.UpdateTodoModel
import com.axel.todo.specification.TodoSpecification
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.streams.toList

@RestController
@RequestMapping("task")
class TodoController {
    @GetMapping("title")
    fun searchTodoByTitle(@RequestParam("title", required = true) title:String,
                          @RequestParam(value = "page", defaultValue = "0", required = false)page:Int,
                          @RequestParam(value = "page_size", defaultValue = "10", required = false)pageSize:Int):ResponseEntity<Any>{
        val response = ApiResponse(HttpStatus.NOT_FOUND.value(),"This Task Does Not Exist",null)
        val pageTask = todoService.searchByTitle(title, pageable = PageRequest.of(page,pageSize, Sort.by("id")))
        if(pageTask.isEmpty){
            return ResponseEntity(response,HttpStatus.NOT_FOUND)
        }
        val data = pageTask.get().map {TodoDto.fromTodoEntity(it)}.toList()
        val result =ApiResponse(HttpStatus.OK.value(),"Task Found",data)
        return ResponseEntity(result,HttpStatus.OK)
    }

    @GetMapping
    fun findTodos(@RequestParam("id", required = false) noteId: Long?,
                  @RequestParam("username", required = false) username: String?,
                  @RequestParam(value = "page", defaultValue = "0", required = false)page:Int,
                  @RequestParam(value = "page_size", defaultValue = "10", required = false) pageSize:Int) : ResponseEntity<Any> {

        val todos :List<TodoEntity> = if(username != null) {
            todoService.getTodosByUsername(username, PageRequest.of(page, pageSize)).get().toList()
        } else if (noteId != null) {
            todoService.getTodosById(noteId).map { listOf(it) }.orElse(listOf())
        } else {
            todoService.getAllTodos()
        }



        if(todos.isEmpty()) return ResponseEntity.notFound().build()

        val map : MutableMap<String, Any> = HashMap()

        map["todos"] = todos

        val result = ApiResponse(HttpStatus.OK.value(),"Successful", todos.map { TodoDto.fromTodoEntity(it) }.toList())

        return ResponseEntity.ok(result)
    }
    @PostMapping("create")
    fun createTodo(@Valid @RequestBody todoModel:TodoModel):ResponseEntity<Any>{
        val newTask = todoService.createTodo(todoModel)
        val result = ApiResponse(HttpStatus.CREATED.value(),"This Task ${todoModel.title} has been Created",TodoDto.fromTodoEntity(newTask))
        return ResponseEntity(result,HttpStatus.CREATED)
    }

    @PutMapping("update")
    fun updateTodo(@Valid @RequestBody updateTodoModel: UpdateTodoModel):ResponseEntity<Any>{
        val existingTask = todoService.updateTodo(updateTodoModel)
        val result = ApiResponse(HttpStatus.OK.value(),"This Task ${updateTodoModel.title} has been Updated",TodoDto.fromTodoEntity(existingTask))

        return ResponseEntity(result,HttpStatus.OK)
    }

}