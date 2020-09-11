package com.example.image

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1



    // Easier way to take a thumbnail-image
    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
        result -> imageView.setImageBitmap(result)
    }

    private val fileName = "temp_photo"
    private val imgPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    private var imageFile: File? = null
    private val mCurrentPhotoPath = imageFile!!.absolutePath

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageFile = File.createTempFile(fileName,".jpg",imgPath)

        if(imageFile != null){
            val photoURI: Uri = FileProvider.getUriForFile(this,
                    "com.example.image.fileprovider",
                    imageFile!!)

            val myIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            button.setOnClickListener{
                if(myIntent.resolveActivity(packageManager) != null){
                    myIntent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    myIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI)
                    startActivityForResult(myIntent,REQUEST_IMAGE_CAPTURE)
                }
                // takePicture.launch(null)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, recIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, recIntent)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val extras = recIntent!!.extras
            val imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
            imageView.setImageBitmap(imageBitmap)
        }
    }
}