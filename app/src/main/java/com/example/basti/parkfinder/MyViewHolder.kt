package com.example.basti.parkfinder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.basti.parkfinder.Model.LotItem
import kotlinx.android.synthetic.main.lot_table_cell.view.*


class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(entry: LotItem) {
        itemView.lotName.text = entry.mLotName
        itemView.streetName.text = entry.mAdress.street
    }

}