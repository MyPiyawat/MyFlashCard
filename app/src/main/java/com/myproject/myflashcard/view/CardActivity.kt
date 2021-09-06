package com.myproject.myflashcard.view

import android.graphics.Bitmap

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.google.android.material.textfield.TextInputEditText
import com.myproject.myflashcard.R
import com.myproject.myflashcard.model.CardModel
import com.myproject.myflashcard.viewModel.CardViewModel
import com.myproject.myflashcard.viewModel.DeckViewModel


class CardActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var imageUri: Uri? = null
    private lateinit var acceptBtn : Button
    private lateinit var answerEdt : TextInputEditText
    private var cardViewModel: CardViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        init()
    }

    private fun init() {
        //initial data
        val id = intent.getIntExtra(DecksActivity.DECK_ID,-1)
        cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        //Initial Image
        imageView = findViewById(R.id.img_data)
        imageView.setOnClickListener { startCrop() }

        //Initial Button
        acceptBtn = findViewById(R.id.btn_accept)
        acceptBtn.setOnClickListener { savingData(id) }

        //Initial Edittext
        answerEdt = findViewById(R.id.edt_answer)


    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            imageUri = result.uriContent

            imageView.setImageURI(imageUri)
            //val cole = this.contentResolver.openInputStream(uriContent!!)?.readBytes()!!
        } else {
            // an error occurred
            result.error
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

    private fun savingData(id : Int){
        val data = this.contentResolver.openInputStream(imageUri!!)?.readBytes()!!
        val answer : String = answerEdt.text.toString()

        val card = CardModel(data,answer,id)

        cardViewModel?.createCard(card)

        finish()
    }

}