package com.example.piccheck.ui.notifications

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.piccheck.R
import com.example.piccheck.Reminder
import com.example.piccheck.ui.home.ReminderAdapter

class CompletedReminderAdapter(private var reminders: List<Reminder>, private val imagePickerListener: CompletedReminderAdapter.ImagePickerListener) : RecyclerView.Adapter<CompletedReminderAdapter.CompletedReminderViewHolder>()  {

    class CompletedReminderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewGoal: TextView = view.findViewById(R.id.textViewGoal)
        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
        val hiddenSection: LinearLayout = view.findViewById(R.id.hiddenSection)
        val buttonTakePicture: Button = view.findViewById(R.id.buttonTakePicture)
        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
        val buttonComplete: Button = view.findViewById(R.id.buttonComplete)
        val editTextReflection: EditText = view.findViewById(R.id.editTextReflection)

        init {
            itemView.setOnClickListener {
                //Open Reminder
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false)
        return CompletedReminderAdapter.CompletedReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompletedReminderViewHolder, position: Int) {        val reminder = reminders[position]
        holder.textViewGoal.text = reminder.goal
        holder.textViewDate.text = reminder.date ?: "No Date"
        holder.hiddenSection.visibility = View.GONE


    }

    override fun getItemCount(): Int {
        return reminders.size
    }


    interface ImagePickerListener {
        fun onTakePictureClicked(id: String)
        fun writeDataToFile(data: String)

        fun alertForMissingPicture()
        fun refreshRemindersDisplay()
    }
}