package com.example.piccheck

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.*

@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val goal: String,
    val date: String? = null,
    val reflection: String? = null,
    val completed: Boolean,
    val picture: Bitmap? = null
)