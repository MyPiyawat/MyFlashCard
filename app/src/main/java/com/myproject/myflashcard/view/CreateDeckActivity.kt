package com.myproject.myflashcard.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.myproject.myflashcard.R
import com.myproject.myflashcard.model.DeckModel
import com.myproject.myflashcard.utils.DeckCategoryUtil
import com.myproject.myflashcard.viewModel.DeckViewModel

class CreateDeckActivity : AppCompatActivity() {

    lateinit var categorySpinner : Spinner
    private lateinit var nameEditText: TextInputEditText
    lateinit var createButton : Button
    private var deckViewModel: DeckViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_deck)

        init()
    }

    private fun init(){
        deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)

        //initial Spinner
        val reader = DeckCategoryUtil(applicationContext)
        var listOfItems = reader.getAllCategoryString()
        categorySpinner = findViewById(R.id.spinner_category)
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,listOfItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        //initial Edit text
        nameEditText = findViewById(R.id.edt_name)
        nameEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                createButton.isEnabled = p0.toString().trim { it <= ' ' }.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        createButton = findViewById(R.id.btn_create)

        createButton.setOnClickListener {
            val name : String = nameEditText.text.toString()
            val type = categorySpinner.selectedItemPosition
            val deck = DeckModel(name,type,0)

            deckViewModel?.createDeck(deck)
            Toast.makeText(applicationContext, "Success",Toast.LENGTH_SHORT).show()
        }
    }
}