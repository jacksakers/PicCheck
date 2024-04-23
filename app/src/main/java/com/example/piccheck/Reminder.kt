package com.example.piccheck

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.*

@Entity
data class Reminder(
    @PrimaryKey val id: Int,
    val goal: String,
    val date: String? = null,
    val reflection: String? = null,
    val completed: Boolean,
    val picture: Bitmap? = null
)