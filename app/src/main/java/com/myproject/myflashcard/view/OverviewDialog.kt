package com.myproject.myflashcard.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import com.myproject.myflashcard.R
import com.myproject.myflashcard.model.ScoreModel
import org.w3c.dom.Text
import java.text.DecimalFormat

class OverviewDialog : DialogFragment() {

    private var points: Int = 0
    private var size: Int = 0
    private lateinit var scoreModel : ScoreModel
    var onOkClickListener: (() -> Unit)? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initial()
    }

    private fun initial() {
        // dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setCanceledOnTouchOutside(false)



        val scoreCardView = dialog?.findViewById<CardView>(R.id.cv_score)
        val scoreTxt = dialog?.findViewById<TextView>(R.id.txt_score_rating)
        val nameTxt = dialog?.findViewById<TextView>(R.id.txt_score_name)
        val quoteTxt = dialog?.findViewById<TextView>(R.id.txt_about_score)
        val scoreBtn = dialog?.findViewById<Button>(R.id.btn_score)

        scoreCardView?.setCardBackgroundColor(Color.parseColor(scoreModel.color))

        val rate = (points*10.0)/size
        val df = DecimalFormat("#.#")
        val score = df.format(rate).toFloat()
        scoreTxt?.text = score.toString()

        nameTxt?.text = scoreModel.name

        val quote = "You got $points out of $size, ${scoreModel.quote}"
        quoteTxt?.text = quote

        scoreBtn?.setOnClickListener { onOkClickListener?.invoke()  }


    }





    companion object {
        fun newInstance(points: Int, size: Int, scoreModel: ScoreModel): OverviewDialog = OverviewDialog().apply {
            this.points = points
            this.size = size
            this.scoreModel = scoreModel
        }
    }
}