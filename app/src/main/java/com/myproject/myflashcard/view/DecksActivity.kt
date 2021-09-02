package com.myproject.myflashcard.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myproject.myflashcard.R
import com.myproject.myflashcard.adapter.DeckAdapter
import com.myproject.myflashcard.model.DeckModel
import com.myproject.myflashcard.viewModel.DeckViewModel

class DecksActivity : AppCompatActivity() {

    lateinit var addDeck : ImageView
    private lateinit var  deckAdapter: DeckAdapter
    private lateinit var recyclerView : RecyclerView
    private var dataList = mutableListOf<DeckModel>()
    private var deckViewModel: DeckViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decks)

        init()
    }

    private fun init(){
        //initial grid View
        deckViewModel = ViewModelProvider(this).get(DeckViewModel::class.java)
        recyclerView  = findViewById(R.id.recyclerView)
        deckViewModel?.getDecks()?.observe(this, Observer<List<DeckModel>>{this.showAllDecks(it)})


        //initial icon create deck
        addDeck = findViewById(R.id.img_add_deck)
        addDeck.setOnClickListener {
            val intent = Intent(this,CreateDeckActivity::class.java)
            startActivity(intent)
        }


    }

    private fun showAllDecks(data : List<DeckModel>){
        val layoutManager = GridLayoutManager(applicationContext,2)

        deckAdapter = DeckAdapter(applicationContext,data)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = deckAdapter
    }
}