package com.myproject.myflashcard.repository

import android.app.Application
import com.myproject.myflashcard.model.CardModel
import com.myproject.myflashcard.room.card.CardDAO
import com.myproject.myflashcard.room.card.CardDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CardRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var cardDAO : CardDAO?

    init {
        val db = CardDatabase.getDatabase(application)
        cardDAO = db.cardDAO()
    }

    fun getCard(deckId : Int) = cardDAO?.getCards(deckId)

    fun createCard(cardModel: CardModel){
        launch { withContext(Dispatchers.IO){
            cardDAO?.insertCards(cardModel)
        } }
    }

    fun removeCard(cardModel: CardModel){
        launch { withContext(Dispatchers.IO){
            cardDAO?.deleteCard(cardModel)
        } }
    }



}