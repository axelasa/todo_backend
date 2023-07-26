package com.axel.todo.model

data class ApiResponse(
    val status:Int,
    val description:String,
    val data:Any?
)