package com.example.basti.parkfinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.basti.parkfinder.Model.LotEntry

class RecyclerViewAdapter() : RecyclerView.Adapter<MyViewHolder>() {

    private var mLotData = mutableListOf<LotEntry>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.lot_table_cell, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val entry = mLotData[position]
        holder!!.bind(entry.mLotName)
    }

    override fun getItemCount(): Int {
        return mLotData.count()
    }

    fun setItems(list: MutableList<LotEntry>) {
        mLotData = list
        notifyDataSetChanged()
    }

}