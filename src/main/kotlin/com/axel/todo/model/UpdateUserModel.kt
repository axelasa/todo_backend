package com.axel.todo.model

data class UpdateUserModel(
    val firstname:String?,
    val surname:String?,
    val username:String,
    val password:String?,
)
