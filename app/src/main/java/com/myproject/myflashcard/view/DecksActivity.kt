package com.myproject.myflashcard.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myproject.myflashcard.R
import com.myproject.myflashcard.adapter.DeckAdapter
import com.myproject.myflashcard.model.DeckModel
import com.myproject.myflashcard.viewModel.DeckViewModel

class DecksActivity() : AppCompatActivity(), DeckAdapter.OnClickItemListener {

    private lateinit var addDeck: ImageView
    private lateinit var deckAdapter: DeckAdapter
    private lateinit var recyclerView: RecyclerView
    private var deckViewModel: DeckViewModel? = null
    private var type = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decks)

        init()
    }

    private fun init() {
        //initial
        type = intent.getIntExtra(MainMenuActivity.TYPE_MESSAGE, -1)

        //initial grid View
        deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)
        recyclerView = findViewById(R.id.recyclerView)
        deckViewModel?.getDecks()
            ?.observe(this, Observer<List<DeckModel>> { this.showAllDecks(it) })


        //initial icon create deck
        addDeck = findViewById(R.id.img_add_deck)
        if (type == 0) {
            addDeck.isGone = true
        } else {
            addDeck.isVisible = true
            addDeck.setOnClickListener {
                val intent = Intent(this, CreateDeckActivity::class.java)
                startActivity(intent)
            }
        }


    }

    private fun showAllDecks(data: List<DeckModel>) {
        val layoutManager = GridLayoutManager(applicationContext, 2)

        deckAdapter = DeckAdapter(applicationContext, data, this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = deckAdapter
    }

    override fun onClick(deck: DeckModel) {
        val intent = if (type == 0) {
            Intent(this, FlashCardActivity::class.java)
        } else {
            Intent(this, CardListActivity::class.java)
        }.apply {
            putExtra(Companion.DECK_DATA, deck)

        }

        startActivity(intent)
    }

    companion object {
        const val DECK_DATA = "DECK_MODEL"
        const val DECK_ID = "DECK_ID"
    }


}