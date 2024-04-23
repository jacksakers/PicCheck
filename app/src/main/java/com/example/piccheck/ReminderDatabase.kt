package com.example.piccheck;
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Reminder::class], version = 1, exportSchema = false)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao

//    companion object {
//        @Volatile
//        private var INSTANCE: ReminderDatabase? = null
//
//        fun getDatabase(context: Context): ReminderDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    ReminderDatabase::class.java,
//                    "reminder_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
}

object DatabaseProvider {
    @Volatile
    private var instance: ReminderDatabase? = null

    fun getDatabase(context: Context): ReminderDatabase {
        if (instance == null) {
            synchronized(ReminderDatabase::class) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReminderDatabase::class.java,
                    "reminder-database"
                ).build()
            }
        }
        return instance!!
    }
}
