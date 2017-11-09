package com.example.basti.parkfinder

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Basti on 31.10.17.
 */
class RecyclerViewAdapter() : RecyclerView.Adapter<MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.lot_table_cell, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.bind("Test")
    }

    override fun getItemCount(): Int {
        return 50
    }

}