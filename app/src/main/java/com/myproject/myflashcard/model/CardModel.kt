package com.myproject.myflashcard.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
data class CardModel(

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var data: ByteArray,

    @ColumnInfo(name ="answer") var answer: String,

    @ColumnInfo(name ="deck_id") var deckId: Int

){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Int? = null
}


