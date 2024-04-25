package com.example.piccheck
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import com.example.piccheck.Reminder
import com.google.gson.Gson
import java.io.File
import java.io.FileWriter

class ReminderManager(private val context: Context) {


    private val fileName = "reminders.json"

    fun updateReminderImagePath(reminderId: String, imagePath: String) {
        // Read existing reminders from file
        val existingReminders = readRemindersFromFile()

        // Find the reminder with the specified ID
        val updatedReminders = existingReminders.map { reminder ->
            if (reminder.id == reminderId) {
                // Update the imagePath for the reminder with the specified ID
                reminder.copy(imagePath = imagePath)
            } else {
                reminder
            }
        }

        // Convert the updated list to JSON
        val gson = Gson()
        val json = gson.toJson(updatedReminders)

        // Write the updated JSON to the file
        writeToFile(json)

        // Assuming 'imagePreview' is the ImageView for image preview
//        val imagePreview = findViewById<ImageView>(R.id.imagePreview)
//
//        // Set the visibility    of the imagePreview to VISIBLE
//        imagePreview.visibility = View.VISIBLE
//
//        // Load the selected image into the ImageView using Glide
//        Glide.with(this)
//            .load(imagePath.toUri()) // Provide the URI of the selected image
//            .into(imagePreview)
    }

    fun insertReminder(reminder: Reminder) {
        val remindersList = mutableListOf<Reminder>()

        // Read existing reminders from file
        val existingReminders = readRemindersFromFile()

        // Add the new reminder
        remindersList.addAll(existingReminders)
        remindersList.add(reminder)

        // Convert the list to JSON
        val gson = Gson()
        val json = gson.toJson(remindersList)

        // Write JSON to file
        writeToFile(json)
    }

    fun readRemindersFromFile(): List<Reminder> {
        val file = File(context.filesDir, "reminders.json")
        if (!file.exists()) return emptyList()

        val json = file.readText()
        val gson = Gson()
        return gson.fromJson(json, Array<Reminder>::class.java).toList()
    }

    fun writeToFile(data: String) {
        val file = File(context.filesDir, "reminders.json")
        FileWriter(file).use {
            it.write(data)
        }
        Log.d("FILE WRITE:", data)
    }
}
