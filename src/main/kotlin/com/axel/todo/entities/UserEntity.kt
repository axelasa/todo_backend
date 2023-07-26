package com.axel.todo.entities

import com.axel.todo.model.UserModel
import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "user")
 class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val createAt:Date,
    var firstname:String,
    var surname:String,
    val username:String,
    var password:String,

//    @ElementCollection
//    @Column(name = "todos",unique = true)
//    val todos:Set<String> = setOf(),

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    var todo: MutableSet<TodoEntity> = mutableSetOf(),

    var updateAt:Date
){
    companion object{
        fun createNewUser(user: UserModel):UserEntity{
            val now = Date()
            val newUser = UserEntity(null, createAt = now, firstname = user.firstname, surname = user.surname, username = user.username, password = user.password, updateAt = now, todo = mutableSetOf())
            return newUser
        }
    }
}
