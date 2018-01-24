package com.example.basti.parkfinder


import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.SearchView
import com.example.basti.parkfinder.Download.JSONLoader
import com.example.basti.parkfinder.Model.CarPark
import com.example.basti.parkfinder.Model.CarParkItems
import com.example.basti.parkfinder.Model.CarParkViewModel
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_lot_table.*
import kotlinx.android.synthetic.main.fragment_lot_table.view.*


class LotTableFragment : Fragment(), OnItemClickListener {

    var showAd = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)


    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_lot_table, container, false)
        val model = ViewModelProviders.of(activity).get(CarParkViewModel::class.java)

        view.swipeRefresh.setOnRefreshListener { JSONLoader(context).downloadData() }


        JSONLoader(context).downloadData()
        model.getLotData().observe(this, Observer<CarParkItems> { list ->
            Log.d("DOWNLOAD", "SET CHANGED")

            (recyclerView.adapter as RecyclerViewAdapter).setItems(list!!.lots)
            (recyclerView.adapter as RecyclerViewAdapter).notifyDataSetChanged()
            view.swipeRefresh.isRefreshing = false
        })


        val adRequest = AdRequest.Builder().build()
        view.adView.loadAd(adRequest)

        view.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = view.recyclerView.layoutManager as LinearLayoutManager
                if(linearLayoutManager.childCount + linearLayoutManager.findFirstVisibleItemPosition() >= linearLayoutManager.itemCount-5){
                    view.adView.visibility = View.GONE
                }
                else{
                    view.adView.visibility = View.VISIBLE
                }
            }
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.options_menu, menu)

        val searchManager = activity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                (recyclerView.adapter as RecyclerViewAdapter).filter.filter(p0)
                return true
            }

        })
    }


    fun setupRecyclerView() {
        val recyclerView = view!!.recyclerView

        val mgr = LinearLayoutManager(context)
        recyclerView.layoutManager = mgr

        val adapter = RecyclerViewAdapter(this)

        recyclerView.adapter = adapter


    }

    override fun onItemClick(item: CarPark) {
        val ft = activity.supportFragmentManager.beginTransaction()
        val fm = InfoFragment.newInstance()
        fm.carPark = item
        ft.replace(R.id.LotTableFragment, fm).addToBackStack(null)
        ft.commit()

    }



    companion object {

        fun newInstance(): LotTableFragment = LotTableFragment()

    }


}

interface OnItemClickListener {

    fun onItemClick(item: CarPark)

}


