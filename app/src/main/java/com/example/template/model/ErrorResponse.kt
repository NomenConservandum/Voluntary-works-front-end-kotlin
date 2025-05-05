package com.example.template.model

import java.util.Dictionary

data class ErrorResponse(
    val type: String,
    val title: String,
    val code: Int,
    val errors: Dictionary<String, MutableList<String>>
)
