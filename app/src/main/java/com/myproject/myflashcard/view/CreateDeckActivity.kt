package com.myproject.myflashcard.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.myproject.myflashcard.R
import com.myproject.myflashcard.model.DeckModel
import com.myproject.myflashcard.utils.DeckCategoryUtil
import com.myproject.myflashcard.view.DecksActivity.Companion.DECK_DATA
import com.myproject.myflashcard.view.DecksActivity.Companion.DECK_ID
import com.myproject.myflashcard.viewModel.DeckViewModel
import android.app.Activity




class CreateDeckActivity : AppCompatActivity() {


    private val createButton by lazy { findViewById<Button>(R.id.btn_create) }
    private val nameEditText by lazy { findViewById<EditText>(R.id.edt_name) }
    private val categoryAutoComplete by lazy {
        findViewById<AutoCompleteTextView>(R.id.autoComplete_category)
    }
    private var deckViewModel: DeckViewModel? = null
    private var name = ""
    private var selectedPosition = -1
    private var id = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_deck)

        initData()
        initButton()
        initEdittext()
        initDropDown()
    }

    private fun initData() {
        id = intent.getIntExtra(DECK_ID, -1)

        if (id != -1) {
            val deck = intent.getSerializableExtra(DECK_DATA) as DeckModel
            name = deck.name
            selectedPosition = deck.type
        }
    }


    private fun initEdittext() {
        if (id != -1) {
            nameEditText.setText(name)
        }

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

        if (selectedPosition != -1) {
            categoryAutoComplete.setText(listOfItems[selectedPosition])
        }


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
            if (id == -1) {
                createCard()
            } else {
                updateCard()
            }

        }
    }

    private fun updateCard() {
        val deck = DeckModel(name, selectedPosition, 0)
        deck.id = id
        deckViewModel?.updateDeck(deck)

        val returnIntent = Intent()
        returnIntent.putExtra("result", deck)
        setResult(RESULT_OK, returnIntent)
        finish()
    }


    private fun createCard() {
        val deck = DeckModel(name, selectedPosition, 0)
        deckViewModel?.createDeck(deck)
        finish()
    }


}