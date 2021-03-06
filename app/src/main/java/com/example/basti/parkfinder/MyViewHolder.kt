package com.example.basti.parkfinder

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.basti.parkfinder.Model.CarPark
import kotlinx.android.synthetic.main.lot_table_cell.view.*


class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(entry: CarPark, listener: OnItemClickListener) {
        itemView.lotName.text = entry.mLotName
        itemView.streetName.text = entry.mAdress.street
        itemView.freeLots.text = entry.stellplaetze
        entry.details?.let {
            val capacity = it.allocation.capacity?.toDoubleOrNull()
            val stellplaetze = entry.stellplaetze.toDouble()
            var percentage = 0.0
            capacity?.let { percentage = (capacity / stellplaetze) * 100 }

            if (percentage > 100) {
                percentage = 100.0
            }
            val string = String.format("%.0f", percentage)
            itemView.setBackgroundColor(calculateCellColor(percentage))
            val occupiedStr =view.context.getString(R.string.occupied)
            //val occupiedStr = Resources.getSystem().getString(R.string.occupied)+""
            itemView.occupation.text = occupiedStr.plus(string).plus("%")
        }
        view.setOnClickListener { listener.onItemClick(entry) }


    }

    //private val less50 = Color.rgb(174, 196, 76)
    private val less50 = Color.argb(130, 67, 160, 71)
    //private val less50 = Color.parseColor("#8de285")
    //private val less75 = Color.rgb(192, 114, 76)
    private val less75 = Color.argb(130, 251, 140, 0)
    //private val less75 = Color.parseColor7"#e2bf85")
    //private val nearlyFull = Color.rgb(162, 64, 65)
    private val nearlyFull = Color.argb(130, 244, 81, 30)
    //private val nearlyFull = Color.parseColor("#e28585")
    //private val free = Color.rgb(133, 197, 76)
    //private val free = Color.parseColor("#c1edbd")
    private val free = Color.argb(130, 133 ,197,76)
    private val incorrectData = Color.argb(130,255,255,255)
    //private val bljsdn = Color.argb(0.5, 67, 160, 71)


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
            return incorrectData
        }
    }


}