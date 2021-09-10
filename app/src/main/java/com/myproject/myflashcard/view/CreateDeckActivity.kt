package com.myproject.myflashcard.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.myproject.myflashcard.R
import com.myproject.myflashcard.model.DeckModel
import com.myproject.myflashcard.utils.DeckCategoryUtil
import com.myproject.myflashcard.viewModel.DeckViewModel

class CreateDeckActivity : AppCompatActivity() {


    private val createButton by lazy { findViewById<Button>(R.id.btn_create) }
    private var deckViewModel: DeckViewModel? = null
    private var selectedPosition = -1
    private var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_deck)

        initButton()
        initEdittext()
        initDropDown()

    }

    private fun initEdittext() {
        val nameEditText = findViewById<TextInputEditText>(R.id.edt_name)
        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                name = p0.toString()
                createButton.isEnabled =
                    name.trim { it <= ' ' }.isNotEmpty().and(selectedPosition != -1)
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun initDropDown() {
        deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)

        val reader = DeckCategoryUtil(applicationContext)
        val listOfItems = reader.getAllCategoryString()
        val categoryAutoComplete = findViewById<AutoCompleteTextView>(R.id.autoComplete_category)
        val adapter = ArrayAdapter(this, R.layout.item_category_dropdrown, listOfItems)
        categoryAutoComplete.setAdapter(adapter)
        categoryAutoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedPosition = position
                createButton.isEnabled =
                    name.trim { it <= ' ' }.isNotEmpty()
                        .and(selectedPosition != -1)

            }

    }

    private fun initButton() {

        val backImg = findViewById<ImageView>(R.id.img_back)
        backImg.setOnClickListener { onBackPressed() }

        createButton.setOnClickListener {
            createCard()
        }
    }


    private fun createCard() {

        val deck = DeckModel(name, selectedPosition, 0)
        deckViewModel?.createDeck(deck)
        finish()
    }


}