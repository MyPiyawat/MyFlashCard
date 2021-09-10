package com.myproject.myflashcard.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.myproject.myflashcard.model.CardModel
import com.myproject.myflashcard.repository.CardRepository

class CardViewModel (application: Application) : AndroidViewModel(application) {
    private var repository : CardRepository = CardRepository(application)

    fun getCard(deckId : Int) = repository.getCard(deckId)

    fun createCard(card : CardModel) {
        repository.createCard(card)
    }

    fun removeCard(card : CardModel) {
        repository.removeCard(card)
    }

    fun removeCardsByID(cards : List<Int>){
        repository.removeCardsByID(cards)
    }
}