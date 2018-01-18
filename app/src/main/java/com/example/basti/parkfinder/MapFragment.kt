package com.example.basti.parkfinder

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basti.parkfinder.Model.ParkModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.view.*


class MapFragment : Fragment() {

    var mapDelegate: MapDelegate? = null

    fun parkCar() {

        if (ParkModel.readFromFile(context) == null) {
            Log.i("PERSISTANCE", "no object saved")
        }



        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(activity).lastLocation.addOnSuccessListener { location ->
                mapDelegate!!.getMap().addMarker(MarkerOptions().title("My Car").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                        .position(LatLng(location.latitude, location.longitude)))
                ParkModel(location.latitude, location.longitude).saveToFile(context)
                Log.i("PERSISNTACE", "object saved")
            }

        }


    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater!!.inflate(R.layout.fragment_map, container, false)
        view.mapView.onCreate(savedInstanceState)
        mapDelegate = MapDelegate(activity)
        view.mapView.getMapAsync(mapDelegate)
        setupPermission()
        view.parkButton.setOnClickListener { parkCar() }


        return view
    }


    override fun onResume() {
        super.onResume()
        view!!.mapView.onResume()

    }


    override fun onPause() {
        super.onPause()
        view!!.mapView.onPause()
    }

    override fun onStart() {
        super.onStart()
        view!!.mapView.onStart()
    }


    override fun onDetach() {
        super.onDetach()
    }


    fun setupPermission() {

        val permission = ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("PERMISSION", "Permission to map denied")
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Permission to access the microphone is required for this app to record audio.")
                        .setTitle("Permission required")

                builder.setPositiveButton("OK"
                ) { dialog, id ->
                    Log.i("ALERT", "Clicked")
                    makeRequest()
                }

                val dialog = builder.create()
                dialog.show()
            } else {
                makeRequest()

            }
        }
    }

    fun makeRequest() = ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("PERMISSION", "Permission has been denied by user")
                } else {
                    val map = view!!.mapView as GoogleMap
                    val permission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)

                    if (permission == PackageManager.PERMISSION_GRANTED) {
                        map.isMyLocationEnabled = true
                    }
                    Log.i("PERMISSION", "Permission has been granted by user")
                }
            }
        }
    }


    companion object {

        fun newInstance(): MapFragment {
            val fragment = MapFragment()
            return fragment
        }
    }
}// Required empty public constructor
