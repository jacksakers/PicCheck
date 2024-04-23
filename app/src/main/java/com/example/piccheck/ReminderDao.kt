package com.example.piccheck

import androidx.room.*

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders")
    fun getAllReminders(): List<Reminder>

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun insertReminder(reminder: Reminder)

    @Update
    fun updateReminder(reminder: Reminder)

    @Delete
    fun deleteReminder(reminder: Reminder)
}