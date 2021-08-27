package com.myproject.myflashcard.room

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myproject.myflashcard.model.DeckModel

interface DeckDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(deckModel: DeckModel)

    @Query("Select * From deck")
    fun getDecks() : LiveData<DeckModel>
}