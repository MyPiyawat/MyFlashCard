package com.myproject.myflashcard.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myproject.myflashcard.model.DeckCategory
import java.io.IOException

class DeckCategoryUtil (context : Context) {

    var data : List<DeckCategory>

    init {
        val jsonFileString = getJsonDataFromAsset(context, "decktype.json")

        val gson = Gson()

        val listPersonType = object : TypeToken<List<DeckCategory>>() {}.type


        data =  gson.fromJson(jsonFileString, listPersonType)

    }


    fun getDeckCategory(id :Int) : DeckCategory{
        return data[id]
    }

    fun getAllCategoryString() : MutableList<String>{
        val categoryList : MutableList<String> = mutableListOf()
        for (item in data){
            categoryList += item.name
        }

        return categoryList
    }


    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}