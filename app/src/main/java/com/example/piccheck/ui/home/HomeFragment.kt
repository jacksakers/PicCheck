package com.example.piccheck.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.piccheck.databinding.FragmentHomeBinding
import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piccheck.MainActivity
import com.example.piccheck.Reminder
import com.example.piccheck.ReminderManager
import java.io.File
import java.io.FileWriter
import java.util.Calendar
import com.google.gson.Gson

class HomeFragment : Fragment(), ReminderAdapter.ImagePickerListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    val reminderAdapter = ReminderAdapter(listOf(), this)

    private lateinit var reminderManager: ReminderManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reminderManager = ReminderManager(requireContext())

        binding?.recyclerViewReminders?.adapter = reminderAdapter
        binding?.recyclerViewReminders?.layoutManager = LinearLayoutManager(context)

        //Load data from database and update adapter
        val remindersFromFile = readRemindersFromFile()
        val activeReminders = remindersFromFile.filter { !it.completed }  // Filter out completed reminders
        reminderAdapter.updateData(activeReminders)

        binding?.fab?.setOnClickListener {
            toggleReminderInput()
        }

        binding?.buttonAddReminder?.setOnClickListener {
            val goal = binding?.editTextGoal?.text.toString()
            val date = binding?.editTextDate?.text.toString().takeIf { it.isNotEmpty() }
            val id = getRandomString(10)
            val reminder = Reminder(id, goal, date, null,  false)
            // Implement storing the reminder or updating the UI here
            insertReminder(reminder)
        }

        binding?.editTextDate?.setOnClickListener {
            showDatePicker(it)
        }
    }


    fun insertReminder(reminder: Reminder) {
        reminderManager.insertReminder(reminder)
        val remindersFromFile = reminderManager.readRemindersFromFile()
        val activeReminders = remindersFromFile.filter { !it.completed }
        reminderAdapter.updateData(activeReminders)
    }

    private fun readRemindersFromFile(): List<Reminder> {
        return reminderManager.readRemindersFromFile()
    }

    private fun writeToFile(data: String) {
        reminderManager.writeToFile(data)
    }

    override fun writeDataToFile(data: String) {
        writeToFile(data)
    }

    override fun alertForMissingPicture() {
        Toast.makeText(context, "Please attach a picture before completing the reminder.", Toast.LENGTH_LONG).show()
    }

    override fun refreshRemindersDisplay() {
        val remindersFromFile = readRemindersFromFile()
        val activeReminders = remindersFromFile.filter { !it.completed }
        reminderAdapter.updateData(activeReminders)
    }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    public fun showDatePicker(view: View) {
        if (!isAdded) {
            return
        }

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        try {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    // This is called when a date is selected.
                    val selectedDate =
                        String.format("%02d/%02d/%04d", monthOfYear + 1, dayOfMonth, year)
                    binding?.editTextDate?.setText(selectedDate)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        } catch (e: Exception) {
            Log.e("DatePicker", "Error showing DatePickerDiaglog", e)
        }
    }

    private fun toggleReminderInput() {
        binding?.reminderInputLayout?.visibility = if (binding?.reminderInputLayout?.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }
    override fun onDestroyView() {
         super.onDestroyView()
        _binding = null
    }

    fun onImageCaptured(imagePath: String) {
        // Create a new Reminder object with the imagePath and set completed to

        val reminder = Reminder(getRandomString(10),"Goal", "Date", "Reflection", true, imagePath)
        // Insert the reminder to the database or update UI
        Log.d("imagePath that was uploaded: ", imagePath)
//        insertReminder(reminder)
    }


    override fun onTakePictureClicked(id: String) {
        // Call the showImagePickerDialog() function here
        (activity as? MainActivity)?.showImagePickerDialog(id)
    }
}