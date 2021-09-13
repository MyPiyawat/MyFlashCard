package com.myproject.myflashcard.room.deck

import androidx.lifecycle.LiveData
import androidx.room.*
import com.myproject.myflashcard.model.CardModel
import com.myproject.myflashcard.model.DeckModel

@Dao
interface DeckDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(deckModel: DeckModel)

    @Query("Select * From deck ")
    fun getDecks() : LiveData<List<DeckModel>>

    @Update
    suspend fun updateDeck(deckModel: DeckModel)

    @Delete
    suspend fun deleteDeck(deck : DeckModel)
}