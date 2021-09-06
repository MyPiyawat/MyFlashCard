package com.myproject.myflashcard.room.card

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myproject.myflashcard.model.CardModel

@Database(entities = [CardModel::class],version = 1,exportSchema = false)
abstract class CardDatabase : RoomDatabase() {

    abstract fun cardDAO() : CardDAO

    companion object {
        @Volatile
        private var INSTANCE : CardDatabase? = null

        fun getDatabase(context : Context): CardDatabase {
            var tempInstance = INSTANCE

            if(tempInstance != null ) return  tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,CardDatabase::class.java,"card_database").build()

                INSTANCE = instance

                return instance
            }
        }
    }

}