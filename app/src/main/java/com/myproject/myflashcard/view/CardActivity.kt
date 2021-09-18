package com.myproject.myflashcard.view

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.google.android.material.textfield.TextInputEditText
import com.myproject.myflashcard.R
import com.myproject.myflashcard.model.CardModel
import com.myproject.myflashcard.viewModel.CardViewModel


class  CardActivity : AppCompatActivity() {

    private val imageView by lazy { findViewById<ImageView>(R.id.img_data) }
    private val addImageLayout by lazy { findViewById<ConstraintLayout>(R.id.lay_add_image) }
    private val imageCv by lazy { findViewById<CardView>(R.id.cv_data) }
    private val acceptBtn by lazy { findViewById<Button>(R.id.btn_accept) }
    private var imageUri: Uri? = null
    private var answer = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        init()
        initImage()
        initEdittext()

    }

    private fun initEdittext() {
        //Initial Edittext
        val answerEdt = findViewById<TextInputEditText>(R.id.edt_answer)
        answerEdt.addTextChangedListener { text: Editable? ->
            answer = text.toString()
            acceptBtn.isEnabled = answer.trim { it <= ' ' }.isNotEmpty().and(imageCv.isVisible)
        }
    }

    private fun initImage() {
        addImageLayout.setOnClickListener { startCrop() }
        val closeImg = findViewById<ImageView>(R.id.img_close)
        closeImg.setOnClickListener { setUpImageUri(null) }

    }

    private fun init() {
        //initial data
        val id = intent.getIntExtra(DecksActivity.DECK_ID, -1)
        //Initial Button
        acceptBtn.setOnClickListener { savingData(id) }
        val backImg = findViewById<ImageView>(R.id.img_back)
        backImg.setOnClickListener { onBackPressed() }

    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            setUpImageUri(result)

        } else {
            // an error occurred
            result.error
        }
    }

    private fun setUpImageUri(result: CropImageView.CropResult?) {
        imageUri = result?.uriContent
        if (imageUri != null) {
            imageView.setImageURI(imageUri)
            imageCv.visibility = View.VISIBLE
            addImageLayout.visibility = View.GONE
            acceptBtn.isEnabled = answer.trim { it <= ' ' }.isNotEmpty().and(imageUri != null)
        } else {
            addImageLayout.visibility = View.VISIBLE
            imageCv.visibility = View.GONE
        }
    }

    private fun startCrop() {
        cropImage.launch(
            options() {
                setGuidelines(CropImageView.Guidelines.ON)
                setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                setAspectRatio(3, 4)
            }
        )
    }

    private fun savingData(id: Int) {
        val cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)
        val data = this.contentResolver.openInputStream(imageUri!!)?.readBytes()!!

        val card = CardModel(data, answer, id)

        cardViewModel.createCard(card)

        finish()
    }

}