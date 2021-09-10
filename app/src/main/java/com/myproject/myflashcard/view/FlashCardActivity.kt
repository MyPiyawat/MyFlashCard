package com.myproject.myflashcard.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.myproject.myflashcard.R
import com.myproject.myflashcard.adapter.CardAdapter
import com.myproject.myflashcard.adapter.FlashCardAdapter
import com.myproject.myflashcard.model.DeckModel
import com.myproject.myflashcard.viewModel.CardViewModel
import com.yuyakaido.android.cardstackview.*

import java.util.*


class FlashCardActivity : AppCompatActivity(), CardStackListener {

    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val cardStackView by lazy { findViewById<CardStackView>(R.id.card_stack_view) }
    private val showButton by lazy { findViewById<CardStackView>(R.id.btn_show_answer) as Button }
    private val maskView by lazy { findViewById<CardStackView>(R.id.view_mask) as View }
    private lateinit var answerTxt: TextView
    private lateinit var cardAdapter: FlashCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card)

        init()
    }

    private fun init() {
        val deck = intent.getSerializableExtra(DecksActivity.DECK_DATA) as DeckModel

        val id = deck.id

        answerTxt = findViewById(R.id.txt_answer)

        val cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)
        cardViewModel.getCard(id!!)?.observe(this, {
            val randomIt = it.shuffled()
            cardAdapter = FlashCardAdapter(randomIt)
            initCardStack()
            initButton()
        })


    }

    private fun initButton() {
        val correctBtn: ImageView = findViewById(R.id.img_correct_btn)
        correctBtn.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }


        val incorrectBtn: ImageView = findViewById(R.id.img_incorrect_btn)
        incorrectBtn.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        showButton.setOnClickListener {
            showButton.isGone = true
            maskView.isGone = true
            setUpAnswer()
        }
    }

    private fun setUpAnswer() {
        val position = manager.topPosition
        val answer = cardAdapter.getItemDetail(position).answer
        answerTxt.text = answer
    }

    private fun initCardStack() {
        with(manager) {
            manager.setTranslationInterval(8.0f)
            manager.setScaleInterval(0.95f)
            manager.setSwipeThreshold(0.3f)
            manager.setMaxDegree(20.0f)
            setDirections(Direction.HORIZONTAL)
            setStackFrom(StackFrom.Top)
            setCanScrollHorizontal(true)
            setCanScrollVertical(true)
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())

            setVisibleCount(3)
        }
        cardStackView.layoutManager = manager
        cardStackView.adapter = cardAdapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false

            }
        }
    }


    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {

        showButton.isVisible = true
        maskView.isVisible = true


    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }
}