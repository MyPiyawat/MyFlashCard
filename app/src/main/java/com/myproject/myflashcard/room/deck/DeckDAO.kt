package com.myproject.myflashcard.room.deck

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myproject.myflashcard.model.DeckModel

@Dao
interface DeckDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(deckModel: DeckModel)

    @Query("Select * From deck ")
    fun getDecks() : LiveData<List<DeckModel>>
}