package com.example.template.model

import java.time.LocalDateTime

data class PublicRequest (
    val id: Int,
    val adminId: Int,
    val address: String,
    val date: String, // LocalDateTime
    val deadLine: String,
    val pointNumber: Int,
    val respondedPeople: Int,
    val neededPeopleNumber: Int,
    val description: String,
    val isComplited: Boolean, // not my typo ;C
    val isFailed: Boolean,
)