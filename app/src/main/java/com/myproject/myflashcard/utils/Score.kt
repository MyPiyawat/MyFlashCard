package com.myproject.myflashcard.utils

enum class Score (val scoreName : String, val color : String , val start : Float, val end : Float,val quote : String ){
    Poor("Poor","#ee6055", 0.0f , 4.0f , "Keep trying"),
    Fair("Fair","#ffd97d", 4.1f , 6.0f , "Not Bad"),
    Good("Good","#aaf683", 6.1f , 8.0f , "Well Done"),
    Excellent("Excellent","#60d394", 8.1f , 10.0f , "Brilliant!")
}
