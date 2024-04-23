package com.example.piccheck

import androidx.room.*

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders")
    suspend fun getAllReminders(): List<Reminder>

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)
}