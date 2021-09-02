package com.myproject.myflashcard.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView

import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.myproject.myflashcard.R


class CardActivity : AppCompatActivity() {


    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        init()
    }

    private fun init() {
        //Initial Image
        imageView = findViewById(R.id.img_data)
        imageView.setOnClickListener { startCrop() }


    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            val uriContent = result.uriContent
            imageView.setImageURI(uriContent)
        } else {
            // an error occurred
            val exception = result.error
        }
    }

    private fun startCrop() {
        cropImage.launch(
            options() {
                setGuidelines(CropImageView.Guidelines.ON)
                setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                setAspectRatio(2, 3)
            }
        )
    }


}