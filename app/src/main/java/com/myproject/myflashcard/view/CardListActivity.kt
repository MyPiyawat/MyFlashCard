package com.myproject.myflashcard.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myproject.myflashcard.R
import com.myproject.myflashcard.adapter.CardAdapter
import com.myproject.myflashcard.model.CardModel
import com.myproject.myflashcard.model.DeckModel
import com.myproject.myflashcard.view.DecksActivity.Companion.DECK_DATA
import com.myproject.myflashcard.view.DecksActivity.Companion.DECK_ID
import com.myproject.myflashcard.viewModel.CardViewModel
import com.myproject.myflashcard.viewModel.DeckViewModel


class CardListActivity : AppCompatActivity() {

    private var cardViewModel: CardViewModel? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private val initDeck by lazy { intent.getSerializableExtra(DECK_DATA) as DeckModel }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)

        init()
    }

    private fun init() {


        cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        //initial header
        var headerTxt: TextView = findViewById(R.id.txt_header)
        val name = initDeck.name
        headerTxt.text = "$name's Card"

        //initial Button
        val id = initDeck.id
        val addImg = findViewById<ImageView>(R.id.img_add_card)
        addImg.setOnClickListener { openToAddCard() }

        //initial Grid View
        recyclerView = findViewById(R.id.recyclerView_cards)
        cardViewModel?.getCard(id!!)?.observe(this, Observer<List<CardModel>> {
            this.showAllCards(it)
        })

    }



    private fun openToAddCard() {
        val intent = Intent(this, CardActivity::class.java).apply {
            putExtra(DECK_ID, initDeck.id)
        }
        startActivity(intent)
    }

    private fun showAllCards(data: List<CardModel>) {
        val layoutManager = GridLayoutManager(applicationContext, 3)

        cardAdapter = CardAdapter(data, false)
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


}