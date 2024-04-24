package com.example.piccheck.ui
//
//import android.util.Log
//import com.example.piccheck.Reminder
//import com.google.gson.Gson
//import java.io.File
//import java.io.FileWriter
//
//class ReminderFileIO {
//
//
//    fun insertReminder(reminder: Reminder) {
//        val remindersList = mutableListOf<Reminder>()
//
//        // Read existing reminders from file
//        val existingReminders = readRemindersFromFile()
//
//        // Add the new reminder
//        remindersList.addAll(existingReminders)
//        remindersList.add(reminder)
//
//        // Convert the list to JSON
//        val gson = Gson()
//        val json = gson.toJson(remindersList)
//
//        // Write JSON to file
//        writeToFile(json)
//    }
//
//     fun readRemindersFromFile(): List<Reminder> {
//        val file = File(requireContext().filesDir, "reminders.json")
//        if (!file.exists()) return emptyList()
//
//        val json = file.readText()
//        val gson = Gson()
//        return gson.fromJson(json, Array<Reminder>::class.java).toList()
//    }
//
//     fun writeToFile(data: String) {
//        val file = File(requireContext().filesDir, "reminders.json")
//        FileWriter(file).use {
//            it.write(data)
//        }
//        Log.d("WriteToFile", "Data written to file: $data")
//
//    }
//}