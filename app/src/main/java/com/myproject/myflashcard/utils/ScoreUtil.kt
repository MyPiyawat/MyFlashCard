package com.myproject.myflashcard.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import com.myproject.myflashcard.model.ScoreModel
import java.io.IOException

class ScoreUtil(context: Context) {

    val data: List<ScoreModel>

    init {
        val jsonFileString = getJsonDataFromAsset(context, "score.json")

        val gson = Gson()

        val listPersonType = object : TypeToken<List<ScoreModel>>() {}.type

        data = gson.fromJson(jsonFileString, listPersonType)

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

    fun getData(score: Float): ScoreModel {
        var scoreModel: ScoreModel? = null

        for (item in data) {
            if (score >= item.start && score <= item.end) {
                scoreModel = item
            }
        }

        return scoreModel!!
    }
}