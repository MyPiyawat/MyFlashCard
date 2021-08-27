package com.myproject.myflashcard.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
data class CardModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Int? = null,

    @ColumnInfo(name = "data") var data: String,

    @ColumnInfo(name ="answer") var answer: String,

    @ColumnInfo(name ="deck_id") var deckId: Int

)
