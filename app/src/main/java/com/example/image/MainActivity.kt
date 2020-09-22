package com.example.image

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 99

    val myIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{
            if(myIntent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(myIntent, REQUEST_IMAGE_CAPTURE)
            }
            // takePicture.launch(null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, recIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, recIntent)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val extras = recIntent!!.extras
            val imageBitmap = extras!!.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }
}