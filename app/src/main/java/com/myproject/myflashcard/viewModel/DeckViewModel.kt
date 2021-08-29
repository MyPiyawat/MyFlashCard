package com.myproject.myflashcard.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.myproject.myflashcard.model.DeckModel
import com.myproject.myflashcard.repository.DeckRepository

class DeckViewModel (application: Application): AndroidViewModel(application) {

    private var repository:DeckRepository = DeckRepository(application)

    fun getDecks() = repository.getDecks()

    fun createDeck(deck : DeckModel){
        repository.createDeck(deck)
    }
}