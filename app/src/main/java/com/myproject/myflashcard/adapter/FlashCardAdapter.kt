package com.myproject.myflashcard.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.myproject.myflashcard.R
import com.myproject.myflashcard.model.CardModel

class FlashCardAdapter(private val cards: List<CardModel>) :
    RecyclerView.Adapter<FlashCardAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardAdapter.ViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)

        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: FlashCardAdapter.ViewHolder, position: Int) {
        val card = cards[position]
        val image = BitmapFactory.decodeByteArray(card.data, 0, card.data.size)
        holder.imageView.setImageBitmap(image)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun getItemDetail(position: Int): CardModel {
        return cards[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.img_card)
    }

}