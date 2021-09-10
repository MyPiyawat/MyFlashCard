package com.myproject.myflashcard.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.myproject.myflashcard.R

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        init()
    }

    private fun init() {
        val playBtn: CardView = findViewById(R.id.cv_play)
        val editBtn: CardView = findViewById(R.id.cv_create)

        playBtn.setOnClickListener {
            startToDeck(0)
        }

        editBtn.setOnClickListener {
            startToDeck(1)
        }
    }

    private fun startToDeck(i: Int) {
        val intent = Intent(this,   DecksActivity::class.java).apply {
            putExtra(TYPE_MESSAGE, i)
        }
        startActivity(intent)
    }

    companion object {
        const val TYPE_MESSAGE = "TO_ACTIVITY_MESSAGE"
    }
}