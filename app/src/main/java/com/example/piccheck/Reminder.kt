package com.example.piccheck

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reminder(
    @PrimaryKey val id: Int,
    val goal: String,
    val date: String? = null,
    val reflection: String? = null,
    val completed: Boolean
)