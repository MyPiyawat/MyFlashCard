package com.myproject.myflashcard.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.myproject.myflashcard.R
import com.myproject.myflashcard.model.CardModel

class CardAdapter(private val cards: List<CardModel>, private val isFlash: Boolean) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {

        val rootView = if (isFlash) {
            LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_list, parent, false)
        }


        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
        val card = cards[position]

        //set Image
        val image = BitmapFactory.decodeByteArray(card.data, 0, card.data.size)
        holder.imageView.setImageBitmap(image)

    }


    override fun getItemCount(): Int {
        return cards.size
    }

    fun getItemDetail(position : Int) : CardModel{
        return cards[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.img_card)
    }
}