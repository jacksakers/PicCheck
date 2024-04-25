package com.example.piccheck

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import android.Manifest
import android.util.Log
import android.widget.ImageView
import com.example.piccheck.ui.home.HomeFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var reminderManager: ReminderManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reminderManager = ReminderManager(this)


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
//        binding.btnUploadPhoto.setOnClickListener {
//            // Check camera permission before opening the image picker
//            if (hasCameraPermission()) {
//                showImagePickerDialog()
//            } else {
//                requestCameraPermission()
//            }
//        }

    }


    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // Handle the result of selecting an image from storage
        if (uri != null) {
            // Image was selected successfully
            // Do something with the selected image URI
            Log.d("Image Launcher URI =", uri.toString())

            val reminderImageView = findViewById<ImageView>(R.id.imageViewCamera)
            reminderManager.updateReminderImagePath(currentID, uri.toString(), reminderImageView)

        } else {
            // Image selection was canceled or failed
            // Handle accordingly
        }
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 101
        private const val REQUEST_IMAGE_CAPTURE = 102
    }

    private lateinit var currentPhotoPath: String
    private lateinit var currentID: String


    private fun openImageTaker(id: String) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                Log.e("MainActivity", "Error creating image file: ${ex.message}")
                null
            }
            // Continue only if the File was successfully created
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "com.example.piccheck.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        } else {
            // Handle the case where the camera app is not available
            // Display an error message or fallback behavior
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open the image picker
//                showImagePickerDialog(id)
            } else {
                // Permission denied, handle accordingly (e.g., show an explanation or disable functionality)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            var imageUri: Uri? = null
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
                // Check if the data is null
                if (data != null && data.data != null) {
                    // The image was captured successfully
                    // Do something with the captured image URI
                    imageUri = data.data
                    // Do something with the image URI
                    Log.d("Image URI =", imageUri.toString())
                } else {
                    // If data is null, retrieve the image data from the file you specified
                    val imageFile = File(currentPhotoPath)
                    imageUri = Uri.fromFile(imageFile)
                    // Do something with the image URI
                    Log.d("Image URI =", imageUri.toString())
                }
            }
            Log.d("Image URI!!!!:", imageUri.toString())

            val reminderImageView = findViewById<ImageView>(R.id.imageViewCamera)
            reminderManager.updateReminderImagePath(currentID, imageUri.toString(), reminderImageView)


            // Pass the image URI to HomeFragment
//            val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
//            if (fragment is HomeFragment) {
//                fragment.onImageCaptured(imageUri.toString())
//            }
        }
    }

    fun showImagePickerDialog(id: String) {

        if (hasCameraPermission()) {
            currentID = id

//            val options = arrayOf("Take Photo", "Choose from Gallery")
//
//            AlertDialog.Builder(this)
//                .setTitle("Choose Action")
//                .setItems(options) { dialog, which ->
//                    when (which) {
//                        0 -> openImageTaker(id)// Launch camera for taking a picture
//                        1 -> pickImageLauncher.launch("image/*") // Launch image picker for selecting from gallery
//                    }
//                    dialog.dismiss()
//                }
//                .setNegativeButton("Cancel") { dialog, _ ->
//                    dialog.dismiss()
//                }
//                .show()

            openImageTaker(id)
        } else {
            requestCameraPermission()
        }
    }

}