package com.example.piccheck.ui.home
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.piccheck.R
import com.example.piccheck.Reminder

class ReminderAdapter(private var reminders: List<Reminder>) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    class ReminderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewGoal: TextView = view.findViewById(R.id.textViewGoal)
        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.textViewGoal.text = reminder.goal
        holder.textViewDate.text = reminder.date ?: "No Date"
    }

    override fun getItemCount() = reminders.size

    fun updateData(newReminders: List<Reminder>) {
        reminders = newReminders.sortedWith(compareBy<Reminder>{ it.date == null }.thenBy { it.date })
        notifyDataSetChanged()
    }
}

