package com.myproject.myflashcard.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.myproject.myflashcard.R
import com.myproject.myflashcard.adapter.CardAdapter
import com.myproject.myflashcard.model.CardModel
import com.myproject.myflashcard.model.DeckModel
import com.myproject.myflashcard.view.DecksActivity.Companion.DECK_DATA
import com.myproject.myflashcard.view.DecksActivity.Companion.DECK_ID
import com.myproject.myflashcard.viewModel.CardViewModel
import com.myproject.myflashcard.viewModel.DeckViewModel


class CardListActivity : AppCompatActivity(), CardAdapter.OnLongClickItemListener {

    private var cardViewModel: CardViewModel? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private lateinit var initDeck: DeckModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)

        initDeck = intent.getSerializableExtra(DECK_DATA) as DeckModel

        init()
        initButton()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val id = initDeck.id

        //initial Grid View
        recyclerView = findViewById(R.id.recyclerView_cards)
        cardViewModel?.getCard(id!!)?.observe(this, Observer<List<CardModel>> {
            this.showAllCards(it)
        })

    }

    private fun initButton() {
        val addImg = findViewById<FloatingActionButton>(R.id.btn_add)
        addImg.setOnClickListener { openToAddCard() }

        val backImg = findViewById<ImageView>(R.id.img_back)
        backImg.setOnClickListener { onBackPressed() }

        val cancelTxt = findViewById<TextView>(R.id.txt_cancel)
        cancelTxt.setOnClickListener { onBackPressed() }

        val deleteImg = findViewById<ImageView>(R.id.img_delete)

        deleteImg.setOnClickListener {

            if (cardAdapter.getSelectedItem().isNotEmpty()) {
                onCreateDeleteDialog()
            }
        }

        val moreImg = findViewById<ImageView>(R.id.img_more)
        moreImg.setOnClickListener { onCreateMoreDialog() }

    }

    private fun init() {
        cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)
        //initial header
        var headerTxt: TextView = findViewById(R.id.txt_header)
        val name = initDeck.name
        headerTxt.text = "$name's Card"

    }

    private fun onCreateMoreDialog() {
        val size = cardAdapter.itemCount
        val items = if (size <= 0) {
            arrayOf("Edit Deck", "Delete Deck")
        } else {
            arrayOf("Edit Deck", "Delete Card")
        }


        val builder = AlertDialog.Builder(this)
        with(builder) {
            setItems(items) { _, which ->
                if (items[which] == items[0]) {
                    onUpdateDeck()
                } else if (items[which] == items[1]) {
                    if(size <= 0){
                        onDeleteDeck()
                    }
                    else {
                        cardAdapter.changeMode()
                        true.toSelectedMode()
                    }
                }
            }

            show()
        }

    }

    private fun onDeleteDeck() {
        val deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)
        deckViewModel.deleteDeck(initDeck)
        finish()

    }

    private fun onUpdateDeck() {
        val intent =
            Intent(this, CreateDeckActivity::class.java).apply {
                putExtra(DECK_ID, initDeck.id)
                putExtra(DECK_DATA, initDeck)
            }
        updateResult.launch(intent)
    }


    private fun deleteItem() {
        val selectedCards = cardAdapter.getSelectedItem()
        cardViewModel?.removeCardsByID(selectedCards)
        false.toSelectedMode()
    }


    private fun openToAddCard() {
        val intent = Intent(this, CardActivity::class.java).apply {
            putExtra(DECK_ID, initDeck.id)
        }
        startActivity(intent)
    }

    private fun showAllCards(data: List<CardModel>) {
        val layoutManager = GridLayoutManager(applicationContext, 3)

        cardAdapter = CardAdapter(data,this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = cardAdapter

        if (initDeck.quantity != data.size) {
            updateDeck(data.size)
        }


    }

    private fun updateDeck(amount: Int) {
        val deckViewModel: DeckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)

        initDeck.quantity = amount

        deckViewModel.updateDeck(initDeck)

    }

    override fun onLongClick() {
        true.toSelectedMode()
    }

    override fun onBackPressed() {
        if(cardAdapter.getAdapterMode()){
            false.toSelectedMode()
            cardAdapter.changeMode()
        }
        else {
            super.onBackPressed()
        }

    }

    private fun Boolean.toSelectedMode() {
        val selectGroup = findViewById<androidx.constraintlayout.widget.Group>(R.id.group_select_mode)
        val normalGroup = findViewById<androidx.constraintlayout.widget.Group>(R.id.group_normal_mode)

        if(this){
            selectGroup.visibility = View.VISIBLE
            normalGroup.visibility = View.GONE
        }
        else {
            selectGroup.visibility = View.GONE
            normalGroup.visibility = View.VISIBLE
        }
    }

    private fun onCreateDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Are you sure?")
            setMessage("Do you want to delete these cards?")
            setPositiveButton("Cancel") { dialog, _ -> dialog.dismiss() }
            setNegativeButton("Delete") { _, _ -> deleteItem() }
            show()
        }
    }

    private val updateResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val data = it.data
                val deck = data?.getSerializableExtra("result") as DeckModel
                initDeck = deck
                init()
            }
        }


}