package com.example.piccheck.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.piccheck.databinding.FragmentHomeBinding
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piccheck.R
import com.example.piccheck.Reminder
import java.io.File
import java.io.FileWriter
import java.util.Calendar
import com.google.gson.Gson


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val reminderAdapter = ReminderAdapter(listOf())

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


        binding?.recyclerViewReminders?.adapter = reminderAdapter
        binding?.recyclerViewReminders?.layoutManager = LinearLayoutManager(context)

        //Load data from database and update adapter
        val remindersFromFile = readRemindersFromFile()
        reminderAdapter.updateData(remindersFromFile)

        binding?.fab?.setOnClickListener {
            toggleReminderInput()
        }

        binding?.buttonAddReminder?.setOnClickListener {
            val goal = binding?.editTextGoal?.text.toString()
            val date = binding?.editTextDate?.text.toString().takeIf { it.isNotEmpty() }
            val reminder = Reminder(goal, date, null,  false)
            // Implement storing the reminder or updating the UI here
            insertReminder(reminder)

        }

        binding?.editTextDate?.setOnClickListener {
            showDatePicker(it)
        }
    }

    private fun insertReminder(reminder: Reminder) {
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

        val remindersFromFile = readRemindersFromFile()
        reminderAdapter.updateData(remindersFromFile)
    }

    private fun readRemindersFromFile(): List<Reminder> {
        val file = File(requireContext().filesDir, "reminders.json")
        if (!file.exists()) return emptyList()

        val json = file.readText()
        val gson = Gson()
        return gson.fromJson(json, Array<Reminder>::class.java).toList()
    }

    private fun writeToFile(data: String) {
        val file = File(requireContext().filesDir, "reminders.json")
        FileWriter(file).use {
            it.write(data)
        }
        Log.d("WriteToFile", "Data written to file: $data")

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
}