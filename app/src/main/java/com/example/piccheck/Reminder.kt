package com.example.piccheck

data class Reminder(
    val id: String,
    val goal: String,
    val date: String? = null,
    var reflection: String? = null,
    var completed: Boolean,
    var imagePath: String? = null
)