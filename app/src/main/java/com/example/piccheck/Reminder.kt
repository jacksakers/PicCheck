package com.example.piccheck

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val goal: String,
    val date: String?,
    val additionalInfo: String?,
    val isCompleted: Boolean
)