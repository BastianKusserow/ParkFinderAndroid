package com.example.basti.parkfinder

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.example.basti.parkfinder.Model.CarPark
import com.example.basti.parkfinder.Model.CarParktModelSingleton

class RecyclerViewAdapter(val listener: OnItemClickListener) : RecyclerView.Adapter<MyViewHolder>(), Filterable {
    override fun getFilter(): Filter {
        val filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                var filteredArrayNames = listOf<CarPark>()

                // perform your search here using the searchConstraint String.

                val aConstraint = constraint.toString().toLowerCase()
                val zwsp = CarParktModelSingleton.instance.lotData

                filteredArrayNames = zwsp.lots.filter { it.mLotName.contains(aConstraint, true) }

                Log.i("lotcount", zwsp.lots.size.toString())
                for (lot in zwsp.lots) {
                    Log.i("Lots", lot.mLotName)
                }


                results.count = filteredArrayNames.size
                results.values = filteredArrayNames
                Log.e("VALUES", results.values.toString())

                return results
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                arrayListNames = results!!.values as MutableList<CarPark>
                setItems(arrayListNames)
            }

        }
        return filter
    }

    private var mLotData = CarParktModelSingleton.instance.lotData
    var arrayListNames = CarParktModelSingleton.instance.lotData.lots

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.lot_table_cell, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val entry = arrayListNames[position]
        holder!!.bind(entry, listener)
    }

    override fun getItemCount(): Int {
        return arrayListNames.size
    }

    fun setItems(list: MutableList<CarPark>) {
        arrayListNames = list
        notifyDataSetChanged()
    }


}