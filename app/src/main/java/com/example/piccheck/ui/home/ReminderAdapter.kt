package com.example.piccheck.ui.home

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
import com.google.gson.Gson

class ReminderAdapter(private var reminders: List<Reminder>, private val imagePickerListener: ImagePickerListener) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    private var reflectionText: String? = null
    class ReminderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewGoal: TextView = view.findViewById(R.id.textViewGoal)
        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
        val hiddenSection: LinearLayout = view.findViewById(R.id.hiddenSection)
        val buttonTakePicture: Button = view.findViewById(R.id.buttonTakePicture)
        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
        val buttonComplete: Button = view.findViewById(R.id.buttonComplete)
        val editTextReflection: EditText = view.findViewById(R.id.editTextReflection)

        init {
            itemView.setOnClickListener {
                hiddenSection.visibility = if (hiddenSection.visibility == View.GONE) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.textViewGoal.text = reminder.goal
        holder.textViewDate.text = reminder.date ?: "No Date"
        holder.editTextReflection.setText(reminder.reflection)

        holder.editTextReflection.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                // Update the reflection in the model when text changes
                reminder.reflection = s.toString()
            }
        })


        holder.hiddenSection.visibility = View.GONE

        holder.buttonTakePicture.setOnClickListener {
            imagePickerListener.onTakePictureClicked(reminder.id)
        }

        holder.buttonDelete.setOnClickListener {
            removeReminderAt(position)
        }

        holder.buttonComplete.setOnClickListener {
            completeReminderAt(position)
        }

    }

    private fun removeReminderAt(position: Int) {
        // Update data set and notify adapter
        reminders = reminders.filterIndexed { index, _ -> index != position }
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, reminders.size)
        val gson = Gson()
        val json = gson.toJson(reminders)
        imagePickerListener.writeDataToFile(json)
    }

    private fun completeReminderAt(position: Int) {
        // Modify the reminder to reflect it's complete
        if (reminders[position].imagePath != null) {

            reminders[position].completed = true
            notifyItemChanged(position)
            val gson = Gson()
            val json = gson.toJson(reminders)
            imagePickerListener.writeDataToFile(json)
            imagePickerListener.refreshRemindersDisplay()
        } else {
            //Add alert to make sure they take a picture
            imagePickerListener.alertForMissingPicture()
        }
    }

    override fun getItemCount() = reminders.size

    fun updateData(newReminders: List<Reminder>) {
        reminders = newReminders.sortedWith(compareBy<Reminder>{ it.date == null }.thenBy { it.date })
        notifyDataSetChanged()
    }

    interface ImagePickerListener {
        fun onTakePictureClicked(id: String)
        fun writeDataToFile(data: String)

        fun alertForMissingPicture()
        fun refreshRemindersDisplay()
    }
}
