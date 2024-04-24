package com.example.piccheck.ui.home
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.example.piccheck.R
import com.example.piccheck.Reminder
import android.app.Activity
import android.content.ContentValues


class ReminderAdapter(private var reminders: List<Reminder>) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

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

        holder.hiddenSection.visibility = View.GONE

        holder.buttonTakePicture.setOnClickListener {
            //TODO
//            openCameraOrGallery()
        }

        holder.buttonDelete.setOnClickListener {
            //TODO
            removeReminderAt(position)
        }

        holder.buttonComplete.setOnClickListener {
            //if picture
            //then
            completeReminderAt(position)
        }

    }

    private fun removeReminderAt(position: Int) {
        // Update data set and notify adapter
        reminders = reminders.filterIndexed { index, _ -> index != position }
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, reminders.size)
    }

    private fun completeReminderAt(position: Int) {
        // Modify the reminder to reflect it's complete
        reminders[position].completed = true
        notifyItemChanged(position)
    }

    override fun getItemCount() = reminders.size

    fun updateData(newReminders: List<Reminder>) {
        reminders = newReminders.sortedWith(compareBy<Reminder>{ it.date == null }.thenBy { it.date })
        notifyDataSetChanged()
    }

//    private val REQUEST_IMAGE_CAPTURE = 1
//    private val REQUEST_IMAGE_PICK = 2
//
//    private fun openCameraOrGallery() {
//        val options = arrayOf("Take Picture", "Choose from Gallery")
//        val builder = AlertDialog.Builder(context)
//        builder.setTitle("Select Option")
//        builder.setItems(options) { dialog, which ->
//            when (which) {
//                0 -> takePicture()
//                1 -> chooseFromGallery()
//            }
//        }
//        builder.show()
//    }
//
//    private fun takePicture() {
//        var values = ContentValues()
//        values.put(MediaStore.Images.Media.TITLE, "My Picture")
//        values.put(
//            MediaStore.Images.Media.DESCRIPTION,
//            "Photo taken on " + System.currentTimeMillis()
//        )
//        cameraUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//
//        //this is used to open camera and get image file
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
//        startActivityForResult(cameraIntent, 1)
//    }
//
//    private fun chooseFromGallery() {
//        val pickImageIntent = Intent(
//            Intent.ACTION_PICK,
//            MediaStore.Images.Media.INTERNAL_CONTENT_URI
//        )
//        pickImageIntent.type = "image/*"
//        val mimeTypes = arrayOf("image/jpeg", "image/png")
//        pickImageIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//        startActivityForResult(pickImageIntent, 2)
//    }
}

