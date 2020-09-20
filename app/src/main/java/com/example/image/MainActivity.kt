package com.example.image

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    val TAG = "yayeet"
    val fileName = "temp_photo"

    lateinit var mCurrentPhotoPath: String

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                val imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
                imageView.setImageBitmap(imageBitmap)
            }
        }

    private fun createUri(context: Context, authority: String, imgFile: File?): Uri? {
        return if (imgFile != null) {
            FileProvider.getUriForFile(
                context,
                authority,
                imgFile
            )
        } else {
            null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var imageFile: File? = null
        imageFile = File.createTempFile(fileName, ".jpg", imgPath)
        mCurrentPhotoPath = imageFile!!.absolutePath

        val photoUri = createUri(this, "com.example.image.provider", imageFile)

        if (photoUri != null) {
            button.setOnClickListener {
                takePicture.launch(photoUri)
                // takePicture.launch(null)
            }
        }

    }
}