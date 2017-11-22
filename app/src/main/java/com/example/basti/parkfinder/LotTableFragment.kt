package com.example.basti.parkfinder


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basti.parkfinder.Download.JSONLoader
import com.example.basti.parkfinder.Model.LotEntry
import com.example.basti.parkfinder.Model.LotModel
import kotlinx.android.synthetic.main.fragment_lot_table.view.*


class LotTableFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_lot_table, container, false)
        val model = ViewModelProviders.of(activity).get(LotModel::class.java)

        val recyclerView = view.recyclerView

        val mgr = LinearLayoutManager(context)
        recyclerView.layoutManager = mgr

        val adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter

        view.swipeRefresh.setOnRefreshListener { JSONLoader(context).downloadData() }


        model.getLotData().observe(this, Observer<LotEntry> { list ->
            Log.d("VIEWMODEL", list!!.lots.size.toString())
            adapter.setItems(list)
        })

        JSONLoader(context).downloadData()
       
        return view
    }

    companion object {

        fun newInstance(): LotTableFragment {
            val fragment = LotTableFragment()

            return fragment
        }
    }

}// Required empty public constructor
