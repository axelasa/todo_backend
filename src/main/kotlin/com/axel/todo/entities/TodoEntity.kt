package com.axel.todo.entities

import com.axel.todo.model.TodoModel
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.ToString
import java.util.Date

@Entity
@Table(name = "todo")
class TodoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    var createdAt:Date,
    var title:String,
    var notes:String,
    var updatedAt:Date,


    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
//    @JsonIgnore
    @ToString.Exclude
    val user:UserEntity
){
    companion object{
        fun newTodo(todo: TodoModel, user: UserEntity):TodoEntity{
            val now = Date()
            val task = TodoEntity(null, createdAt = now, title = todo.title, notes = todo.notes, updatedAt = now,user = user)
            return task
        }
    }
}
