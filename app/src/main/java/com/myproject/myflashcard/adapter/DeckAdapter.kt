package com.myproject.myflashcard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myproject.myflashcard.R
import com.myproject.myflashcard.model.DeckModel

class DeckAdapter (private val context: Context, private  val decks : List<DeckModel>?) : RecyclerView.Adapter<DeckAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_deck,parent,false)

        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deck : DeckModel = decks?.get(position) !!

        holder.nameTxt.text = deck.name
        holder.amountTxt.text = "amount : "+deck.quantity

        holder.typeImg.setImageResource(R.drawable.baseline_arrow_back_ios_blue_700_24dp)
    }

    override fun getItemCount(): Int {
        return decks?.size!!
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

        var nameTxt : TextView = itemView.findViewById(R.id.txt_name) as TextView
        var amountTxt : TextView = itemView.findViewById(R.id.txt_amount) as TextView
        var typeImg : ImageView = itemView.findViewById(R.id.img_type) as ImageView
    }
}