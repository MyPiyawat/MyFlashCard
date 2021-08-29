package com.myproject.myflashcard.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myproject.myflashcard.R
import com.myproject.myflashcard.adapter.DeckAdapter
import com.myproject.myflashcard.model.DeckModel

class DecksActivity : AppCompatActivity() {

    private lateinit var  deckAdapter: DeckAdapter
    private var dataList = mutableListOf<DeckModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decks)

        init()
    }

    private fun init(){
        //initial grid View
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(applicationContext,2)

        dataList.add(DeckModel(0,"Field", 2,10))
        dataList.add(DeckModel(1,"Field", 2,10))
        dataList.add(DeckModel(2,"Field", 2,10))
        dataList.add(DeckModel(3,"Field", 2,10))
        dataList.add(DeckModel(4,"Field", 2,10))
        dataList.add(DeckModel(5,"Field", 2,10))
        dataList.add(DeckModel(6,"Field", 2,10))

        deckAdapter = DeckAdapter(applicationContext,dataList)

        recyclerView.adapter = deckAdapter


    }
}