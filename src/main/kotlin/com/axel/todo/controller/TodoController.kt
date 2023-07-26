package com.axel.todo.controller

import com.axel.todo.dto.TodoDto
import com.axel.todo.globalService.GlobalService.Companion.todoService
import com.axel.todo.globalService.GlobalService.Companion.userService
import com.axel.todo.model.ApiResponse
import com.axel.todo.model.TodoModel
import com.axel.todo.model.UpdateTodoModel
import jakarta.validation.Valid
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
//    fun getTodoByTitle(@RequestParam("title", required = true) title:String):ResponseEntity<Any>{
//        val response = ApiResponse(HttpStatus.NOT_FOUND.value(),"This Task Does Not Exist",null)
//        val optionalTask = todoService.getTodosByTitle(title)
//        if(optionalTask.isEmpty){
//            return ResponseEntity(response,HttpStatus.NOT_FOUND)
//        }
//        val result =ApiResponse(HttpStatus.OK.value(),"Task Found",TodoDto.fromTodoEntity(optionalTask.get()))
//        return ResponseEntity(result,HttpStatus.OK)
//    }@GetMapping("title")
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
    @GetMapping("id")
    fun getTodoById(@RequestParam("id", required = true)id:Long):ResponseEntity<Any>{
        val response = ApiResponse(HttpStatus.NOT_FOUND.value(),"This Task Does Not Exist",null)
        val optionalTaskId = todoService.getTodosById(id)
        if(optionalTaskId.isEmpty){
            return ResponseEntity(response,HttpStatus.NOT_FOUND)
        }
        val result =ApiResponse(HttpStatus.OK.value(),"Task Found",TodoDto.fromTodoEntity(optionalTaskId.get()))
        return ResponseEntity(result,HttpStatus.OK)
    }
    @GetMapping
    fun getAllTodo():List<TodoDto> = todoService.getAllTodos().stream().map {
        TodoDto.fromTodoEntity(it)
    }.toList()
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