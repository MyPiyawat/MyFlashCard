package com.myproject.myflashcard.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.myproject.myflashcard.R
import com.myproject.myflashcard.model.CardModel

class CardAdapter(
    private val cards: List<CardModel>,
    private val listener: OnLongClickItemListener
) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    private var isSelectMode = false
    var selectedList = mutableListOf<Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {


        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_list, parent, false)



        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
        val card = cards[position]

        //set Image
        val image = BitmapFactory.decodeByteArray(card.data, 0, card.data.size)
        holder.imageView.setImageBitmap(image)


        if (isSelectMode) {
            holder.checkedImg.visibility = View.VISIBLE
            if (selectedList[position]) {
                holder.checkedImg.setImageResource(R.drawable.round_check_circle_purple_24dp)
                holder.maskedLayout.visibility = View.VISIBLE

            } else {
                holder.checkedImg.setImageResource(R.drawable.round_radio_button_unchecked_white_24dp)
                holder.maskedLayout.visibility = View.GONE
            }

            holder.itemView.setOnClickListener { onSelectedItem(position) }

        } else {
            // holder.maskedLayout.visibility = View.GONE
            holder.checkedImg.visibility = View.GONE
            holder.maskedLayout.visibility = View.GONE
            //holder.imageView.setOnClickListener(null)
        }


        holder.itemView.setOnLongClickListener {

            listener.onLongClick()
            changeMode()
            onSelectedItem(position)

        }

    }

    fun getAdapterMode(): Boolean {
        return isSelectMode
    }

    fun changeMode() {
        beginStart()
        isSelectMode = !isSelectMode
        this.notifyDataSetChanged()

    }

    private fun onSelectedItem(position: Int): Boolean {
        if (isSelectMode) {
            selectedList[position] = !selectedList[position]
            this.notifyDataSetChanged()
        }

        return true
    }


    private fun beginStart() {
        selectedList.clear()
        for (i in cards.indices) {
            selectedList.add(false)
        }
    }

    fun getSelectedItem() : List<Int> {
        val lists = mutableListOf<Int>()
        if (isSelectMode) {
            for (i in selectedList.indices) {
                if (selectedList[i]) {
                    lists.add(cards[i].id!!)
                }
            }
        }

        return lists
    }


    override fun getItemCount(): Int {
        return cards.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.img_card)
        var maskedLayout: View = itemView.findViewById(R.id.lay_masked)
        var checkedImg: ImageView = itemView.findViewById(R.id.img_check)

    }

    interface OnLongClickItemListener {
        fun onLongClick()
    }
}


