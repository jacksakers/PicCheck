package com.example.piccheck

import androidx.room.*

@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true) val key: Int,
    val goal: String,
    val date: String? = null,
    val reflection: String? = null,
    val completed: Boolean,
)