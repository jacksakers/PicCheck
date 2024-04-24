package com.example.piccheck

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.piccheck.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity<File> : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Set click listener for the upload photo button
        binding.btnUploadPhoto.setOnClickListener {
            // Open the options for taking a photo or selecting one from storage
            showImagePickerDialog()

        }

    }


    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // The picture was taken successfully
            // Do something with the taken picture
        } else {
            // Taking picture was canceled or failed
            // Handle accordingly
        }
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // Handle the result of selecting an image from storage
        if (uri != null) {
            // Image was selected successfully
            // Do something with the selected image URI
        } else {
            // Image selection was canceled or failed
            // Handle accordingly
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery")

        AlertDialog.Builder(this)
            .setTitle("Choose Action")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> dispatchTakePictureIntent() // Launch camera for taking a picture
                    1 -> pickImageLauncher.launch("image/*") // Launch image picker for selecting from gallery
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    lateinit var currentPhotoPath: String

//    @Throws(IOException::class)
//    private fun createImageFile(): File {
//        // Create an image file name
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val storageDir: java.io.File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        return File.createTempFile(
//            "JPEG_${timeStamp}_", /* prefix */
//            ".jpg", /* suffix */
//            storageDir /* directory */
//        ).apply {
//            // Save a file: path for use with ACTION_VIEW intents
//            currentPhotoPath = absolutePath
//        }
//    }
    private fun dispatchTakePictureIntent() {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            // Ensure that there's a camera activity to handle the intent
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                // Create the File where the photo should go
//                val photoFile: File? = try {
//                    createImageFile()
//                } catch (ex: IOException) {
//                    // Error occurred while creating the File
//                    null
//                }
//                // Continue only if the File was successfully created
//                photoFile?.also {
//                    val photoURI: Uri = FileProvider.getUriForFile(
//                        this,
//                        "com.example.android.fileprovider",
//                        it
//                    )
//                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                    takePictureLauncher.launch(takePictureIntent)
//                }
//            }
//        }
    }
}