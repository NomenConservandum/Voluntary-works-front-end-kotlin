package com.example.template.model

data class User (
    val id: Int, // Is not entered
    val email: String,
    val password: String,
    val name: String, // ФИО через пробел
    val telegramUrl: String,
    val role: String, // Is not entered in registration
    val group: String, // Is not used at the moment
    val points: Int, // Is not entered in registration
    val finishedRequests: Int // Is not entered in registration
)