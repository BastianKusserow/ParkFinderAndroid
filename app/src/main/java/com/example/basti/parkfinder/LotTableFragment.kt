package com.example.basti.parkfinder


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lot_table.view.*


class LotTableFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_lot_table, container, false)
        val recyclerView = view.recyclerView

        val mgr = LinearLayoutManager(context)
        recyclerView.layoutManager = mgr

        val adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter


        return view
    }

    companion object {

        fun newInstance(): LotTableFragment {
            val fragment = LotTableFragment()

            return fragment
        }
    }

}// Required empty public constructor
