package com.example.template.model

// import java.time.LocalDateTime

data class PrivateRequest (
    val id: Int,
    val adminId: Int,
    val address: String,
    val date: String, // Костыль, а куда деваться
    val deadLine: String,
    val pointNumber: Int,
    val respondedPeople: MutableList<Int> = mutableListOf<Int>(),
    val neededPeopleNumber: Int,
    val description: String,
    val isComplited: Boolean, // not my typo ;C
    val isFailed: Boolean,
    val telegramUrl: String
)