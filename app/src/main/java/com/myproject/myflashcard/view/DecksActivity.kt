package com.myproject.myflashcard.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
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


    private lateinit var deckAdapter: DeckAdapter
    private lateinit var recyclerView: RecyclerView
    private var deckViewModel: DeckViewModel? = null
    private val type by lazy { intent.getIntExtra(MainMenuActivity.TYPE_MESSAGE, -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decks)

        initButton()
        initGridView()
    }

    private fun initButton() {
        val addDeck = findViewById<ImageView>(R.id.img_add_deck)
        if (type == 0) {
            addDeck.isGone = true
        } else {
            addDeck.isVisible = true
            addDeck.setOnClickListener {
                val intent = Intent(this, CreateDeckActivity::class.java)
                startActivity(intent)
            }
        }

        val backImg = findViewById<ImageView>(R.id.img_back)
        backImg.setOnClickListener { onBackPressed() }


    }

    private fun initGridView() {
        //initial grid View
        deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)
        recyclerView = findViewById(R.id.recyclerView)
        deckViewModel?.getDecks()
            ?.observe(this, Observer<List<DeckModel>> {
                val data = if (type == 0) {
                    removeQuantityEmpty(it as MutableList<DeckModel>)
                } else {
                    it
                }

                showAllDecks(data)
            })
    }

    private fun removeQuantityEmpty(data: MutableList<DeckModel>): List<DeckModel> {

        for (i in data.indices) {
            if (data[i].quantity == 0) {
                data.removeAt(i)
            }
        }

        return data
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
            putExtra(DECK_DATA, deck)

        }

        startActivity(intent)
    }

    companion object {
        const val DECK_DATA = "DECK_MODEL"
        const val DECK_ID = "DECK_ID"
    }


}