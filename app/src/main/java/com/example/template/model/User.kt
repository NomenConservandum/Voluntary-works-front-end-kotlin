package com.example.template.model

data class User (
    val id: Int, // Is not entered
    val email: String,
    val password: String,
    val Name: String, // ФИО через пробел
    val TelegramUrl: String,
    val Role: String, // Is not entered in registration
    val Group: String, // Is not used at the moment
    val Points: Int, // Is not entered in registration
    val FinishedRequests: Int // Is not entered in registration
)