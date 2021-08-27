package com.myproject.myflashcard.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deck")
data class DeckModel(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") var id:Int? = null,

        @ColumnInfo(name = "name") var name:String,

        @ColumnInfo(name = "type") var type: Int,

        @ColumnInfo(name = "quantity") var quantity: Int
)

