package com.example.basti.parkfinder


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.basti.parkfinder.Model.CarPark
import com.google.maps.model.LatLng

import kotlinx.android.synthetic.main.fragment_info.view.*


class InfoFragment : Fragment() {

    var carPark: CarPark? = null
    var hideFab = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_info, container, false)

        if (hideFab) {
            view.fab.visibility = View.INVISIBLE
        }

        view.fab.setOnClickListener { showMapView() }


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.clear()
    }

    fun setupLabels() {
        carPark?.let {
            view!!.name.text = it.mLotName
            view!!.adress.text = "${it.mAdress.street}, ${it.mAdress.city}"
            view!!.openingHours.text = it.openingHours
            view!!.space.text = it.stellplaetze
            view!!.occupied.text = it.details?.allocation?.capacity
        }
        setupPriceLabels()

    }

    private fun setupPriceLabels(){
        carPark?.let {
            for (item in it.tariffPrices){
                when (item.duration){
                    "30min" -> if(item.price != null)view!!.halfHourTV.text = item.price.toString().plus("€")
                    "1hour" -> if(item.price != null)view!!.hourTV.text = item.price.toString().plus("€")
                    "1day" -> if(item.price != null)view!!.dayTV.text = item.price.toString().plus("€")
                    "1week" -> if(item.price != null)view!!.weekTV.text = item.price.toString().plus("€")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setupLabels()
    }

    fun showMapView() {
        val fm = MapFragment()
        fm.hideParkButton = true
        val ft = activity.supportFragmentManager.beginTransaction()
        fm.destination = LatLng(carPark!!.geoLocation.latitude, carPark!!.geoLocation.longitude)
        ft.replace(R.id.LotTableFragment, fm).addToBackStack(null)
        ft.commit()
    }

    companion object {


        fun newInstance(): InfoFragment {
            val fragment = InfoFragment()

            return fragment
        }
    }


}// Required empty public constructor
