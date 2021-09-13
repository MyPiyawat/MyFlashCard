package com.myproject.myflashcard.repository

import android.app.Application
import com.myproject.myflashcard.model.DeckModel
import com.myproject.myflashcard.room.deck.DeckDAO
import com.myproject.myflashcard.room.deck.DeckDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DeckRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var deckDAO: DeckDAO?

    init {
        val db = DeckDatabase.getDatabase(application)
        deckDAO = db.deckDao()
    }

    fun getDecks() = deckDAO?.getDecks()

    fun createDeck(deckModel: DeckModel) {
        launch {
            withContext(Dispatchers.IO) {
                deckDAO?.insertData(deckModel)
            }
        }
    }

    fun updateDeck(deckModel: DeckModel) {
        launch {
            withContext(Dispatchers.Main) {
                deckDAO?.updateDeck(deckModel)
            }
        }
    }

    fun deleteDeck(deckModel: DeckModel){
        launch {
            withContext(Dispatchers.IO){
                deckDAO?.deleteDeck(deckModel)
            }
        }
    }


}