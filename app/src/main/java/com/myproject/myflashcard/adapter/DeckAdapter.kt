package com.myproject.myflashcard.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.myproject.myflashcard.R
import com.myproject.myflashcard.model.DeckModel
import com.myproject.myflashcard.utils.DeckCategory

class DeckAdapter(
    private val context: Context,
    private val decks: List<DeckModel>,
    private val listener: OnClickItemListener
) : RecyclerView.Adapter<DeckAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_deck, parent, false)

        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deck: DeckModel = decks[position]

        holder.nameTxt.text = deck.name
        holder.amountTxt.text = "amount : " + deck.quantity

        holder.nameTxt.text

        val categories = DeckCategory.values()
        val category = categories[deck.type]

        holder.deckCV.setCardBackgroundColor(Color.parseColor(category.color))
        holder.typeImg.setImageResource(category.icon)


        holder.deckCV.setOnClickListener { listener.onClick(deck) }
    }

    override fun getItemCount(): Int {
        return decks.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nameTxt: TextView = itemView.findViewById(R.id.txt_name) as TextView
        var amountTxt: TextView = itemView.findViewById(R.id.txt_amount) as TextView
        var typeImg: ImageView = itemView.findViewById(R.id.img_type) as ImageView
        var deckCV: CardView = itemView.findViewById(R.id.cv_deck) as CardView
    }



    interface OnClickItemListener {
        fun onClick(deck :DeckModel)
    }


}