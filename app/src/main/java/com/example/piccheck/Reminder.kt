package com.example.piccheck

data class Reminder(
    val goal: String,
    val date: String? = null,
    val reflection: String? = null,
    var completed: Boolean
)