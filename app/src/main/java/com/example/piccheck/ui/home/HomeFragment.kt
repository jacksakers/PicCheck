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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piccheck.DatabaseProvider
import com.example.piccheck.Reminder
import com.example.piccheck.ReminderDao
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var reminderDao: ReminderDao

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

        val database = DatabaseProvider.getDatabase(requireContext())
        reminderDao = database.reminderDao()

        val reminderAdapter = ReminderAdapter(listOf())
        binding?.recyclerViewReminders?.adapter = reminderAdapter
        binding?.recyclerViewReminders?.layoutManager = LinearLayoutManager(context)

        //Load data from database and update adapter
//        reminderDao = repository.reminderDao

        // Load data from database and update adapter
        lifecycleScope.launch {
            val loadedData = reminderDao.getAllReminders()
            reminderAdapter.updateData(loadedData)
        }

        binding?.fab?.setOnClickListener {
            toggleReminderInput()
        }

        binding?.buttonAddReminder?.setOnClickListener {
            val goal = binding?.editTextGoal?.text.toString()
            val date = binding?.editTextDate?.text.toString().takeIf { it.isNotEmpty() }
            val reminder = Reminder(0, goal, date, null,  false)
            // Implement storing the reminder or updating the UI here
            insertReminder(reminder)

        }

        binding?.editTextDate?.setOnClickListener {
            showDatePicker(it)
        }
    }

    private fun insertReminder(reminder: Reminder) {
        lifecycleScope.launch {
            reminderDao.insertReminder(reminder)
        }
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
                        String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year)
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