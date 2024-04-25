package com.example.piccheck.ui.notifications

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.piccheck.R
import com.example.piccheck.Reminder
import com.example.piccheck.ui.home.ReminderAdapter

class CompletedReminderAdapter(private var reminders: List<Reminder>, private val imagePickerListener: CompletedReminderAdapter.ImagePickerListener) : RecyclerView.Adapter<CompletedReminderAdapter.CompletedReminderViewHolder>()  {

    class CompletedReminderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewGoal: TextView = view.findViewById(R.id.textViewGoal)
        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
        val buttonTakePicture: Button = view.findViewById(R.id.buttonTakePicture)
        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
        val buttonComplete: Button = view.findViewById(R.id.buttonComplete)
        val editTextReflection: EditText = view.findViewById(R.id.editTextReflection)

        val reminderDetails: LinearLayout = view.findViewById(R.id.reminderDetails)
        val detailTextViewGoal: TextView = view.findViewById(R.id.detailTextViewGoal)
        val detailTextViewDate: TextView = view.findViewById(R.id.detailTextViewDate)
        val detailTextViewReflection: TextView = view.findViewById(R.id.detailTextViewReflection)
        val detailImageView: ImageView = view.findViewById(R.id.detailImageView)

        init {
            itemView.setOnClickListener {
                Log.d("Felt a click", "YEs")
                reminderDetails.visibility = if (reminderDetails.visibility == View.GONE) View.VISIBLE else View.GONE
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false)
        return CompletedReminderAdapter.CompletedReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompletedReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.textViewGoal.text = reminder.goal
        holder.textViewDate.text = reminder.date ?: "No Date"

        holder.detailTextViewGoal.text = reminder.goal
        holder.detailTextViewDate.text = reminder.date ?: "No Date"
        holder.detailTextViewReflection.text = reminder.reflection ?: ""
        try {
            holder.detailImageView.setImageURI(reminder.imagePath?.toUri())
        } catch (e: Exception) {
            // Handle the exception here
            Log.e("setImageURI", "Error loading image: ${e.message}")
        }

        holder.reminderDetails.visibility = View.GONE

//        // Set click listener for each item
//        holder.itemView.setOnClickListener {
//            // Notify the listener about the click event and pass the reminder data
//            imagePickerListener.onReminderClicked(reminder)
//        }
    }

    override fun getItemCount(): Int {
        return reminders.size
    }


    interface ImagePickerListener {
        fun onTakePictureClicked(id: String)
        fun writeDataToFile(data: String)

        fun alertForMissingPicture()
        fun refreshRemindersDisplay()

        fun onReminderClicked(reminder: Reminder)

    }
}