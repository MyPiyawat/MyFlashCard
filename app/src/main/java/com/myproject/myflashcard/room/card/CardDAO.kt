package com.myproject.myflashcard.room.card

import androidx.lifecycle.LiveData
import androidx.room.*
import com.myproject.myflashcard.model.CardModel
import com.myproject.myflashcard.model.DeckModel

@Dao
interface CardDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(deckModel: DeckModel)

    @Query("Select * From card Where deck_id = :deckID")
    fun getCards(deckID:Int) : LiveData<List<CardModel>>

    @Delete
    suspend fun deleteCard(card : CardModel)
}