package com.example.piccheck
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReminderRepository(private val database: ReminderDatabase) {
    private val reminderDao = database.reminderDao()

    suspend fun getAllTasks(): List<Reminder> = withContext(Dispatchers.IO) {
        reminderDao.getAllReminders()
    }

    suspend fun insertTask(reminder: Reminder) = withContext(Dispatchers.IO) {
        reminderDao.insertReminder(reminder)
    }

    suspend fun updateTask(reminder: Reminder) = withContext(Dispatchers.IO) {
        reminderDao.updateReminder(reminder)
    }

    suspend fun deleteTask(reminder: Reminder) = withContext(Dispatchers.IO) {
        reminderDao.deleteReminder(reminder)
    }
}
