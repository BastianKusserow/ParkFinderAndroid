package com.example.basti.parkfinder


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basti.parkfinder.Model.LotItem
import com.google.maps.model.LatLng

import kotlinx.android.synthetic.main.fragment_info.view.*


class InfoFragment : Fragment() {

    var lotItem: LotItem? = null
    var hideFab = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
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

    fun setupLabels() {
        lotItem?.let {
            view!!.name.text = it.mLotName
            view!!.adress.text = "${it.mAdress.street}, ${it.mAdress.city}"
            view!!.openingHours.text = it.openingHours
            view!!.space.text = it.stellplaetze
            view!!.occupied.text = it.details?.allocation?.capacity

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
        fm.destination = LatLng(lotItem!!.geoLocation.latitude, lotItem!!.geoLocation.longitude)
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
