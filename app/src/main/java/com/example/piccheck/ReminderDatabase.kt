package com.example.piccheck;
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Reminder::class], version = 1, exportSchema = false)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao

    companion object {
        private var INSTANCE: ReminderDatabase? = null

        fun getInstance(context: Context): ReminderDatabase? {
            if (INSTANCE == null) {
                synchronized(ReminderDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, ReminderDatabase::class.java, "reminder.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}