package com.example.basti.parkfinder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.lot_table_cell.view.*


class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(text: String){
        itemView.lotName.text = text
    }

}