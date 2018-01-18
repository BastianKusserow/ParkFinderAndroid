package com.example.basti.parkfinder

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.basti.parkfinder.Model.LotItem
import kotlinx.android.synthetic.main.lot_table_cell.view.*


class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(entry: LotItem) {
        itemView.lotName.text = entry.mLotName
        itemView.streetName.text = entry.mAdress.street
        itemView.freeLots.text = entry.stellplaetze
        entry.details?.let {
            val capacity = it.allocation.capacity?.toDoubleOrNull()
            val stellplaetze = entry.stellplaetze.toDouble()
            var percentage = 0.0
            capacity?.let { percentage = (capacity / stellplaetze) * 100 }
            val string = String.format("%.1f", percentage)
            itemView.setBackgroundColor(calculateCellColor(percentage))
            itemView.occupation.text = string
        }


    }

    private val less50 = Color.rgb(174, 196, 76)
    private val less75 = Color.rgb(192, 114, 76)
    private val nearlyFull = Color.rgb(162, 64, 65)
    private val free = Color.rgb(133, 197, 76)

    fun calculateCellColor(percentage: Double): Int {
        val percentageValue = percentage

        if (percentageValue in 0..25) {
            return free
        } else if (percentageValue in 26..50) {
            return less50
        } else if (percentageValue in 51..75) {
            return less75
        } else if (percentageValue in 76..100) {
            return nearlyFull
        } else {
            return Color.GRAY
        }
    }

}