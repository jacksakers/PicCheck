package com.example.piccheck.ui.notifications

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piccheck.Reminder
import com.example.piccheck.databinding.FragmentNotificationsBinding
import com.example.piccheck.ui.home.ReminderAdapter
import com.example.piccheck.R
import com.google.gson.Gson
import java.io.File

class NotificationsFragment : Fragment(), CompletedReminderAdapter.ImagePickerListener {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        binding.recyclerViewCompletedReminders.layoutManager = LinearLayoutManager(context)

        // Load completed reminders
        val completedReminders = loadCompletedReminders()
        val reminderAdapter = CompletedReminderAdapter(completedReminders, this)
        binding.recyclerViewCompletedReminders.adapter = reminderAdapter
    }

    private fun loadCompletedReminders(): List<Reminder> {
        val file = File(requireContext().filesDir, "reminders.json")
        if (!file.exists()) return emptyList()

        val json = file.readText()
        val gson = Gson()
        val allReminders = gson.fromJson(json, Array<Reminder>::class.java).toList()
        return allReminders.filter { it.completed }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTakePictureClicked(id: String) {
        return
    }


    override fun writeDataToFile(data: String) {
        return
    }

    override fun alertForMissingPicture() {
        return
    }

    override fun refreshRemindersDisplay() {
        return
    }


    // Implementation of the interface method to handle item click
    override fun onReminderClicked(reminder: Reminder) {
        // Open the reminder_details view and pass the reminder data
        Log.d("Reminder Clicked:", reminder.goal)
    }

}
