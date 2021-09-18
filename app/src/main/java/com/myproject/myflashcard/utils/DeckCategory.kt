package com.myproject.myflashcard.utils

import com.myproject.myflashcard.R

enum class DeckCategory(val id : Int,val categoryName: String, val color: String, val icon: Int ) {
    Food(0, "Food","#5bcdfa",R.drawable.baseline_set_meal_white_36dp),
    Art(1, "Art & Sport","#f26398",R.drawable.baseline_music_note_white_36dp),
    Thing(2, "Thing","#fcb75d",R.drawable.baseline_emoji_objects_white_36dp),
    Living(3,"Living thing","#5dd39e",R.drawable.baseline_pets_white_36dp),
    Science(4,"Science & Technology","#9667e0",R.drawable.baseline_science_white_36dp),
    Other(5,"Other","#a5a58d",R.drawable.baseline_more_horiz_white_36dp)
}