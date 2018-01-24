package com.example.basti.parkfinder

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.basti.parkfinder.Controller.ParkingController
import com.example.basti.parkfinder.Model.ParkModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.fragment_map.view.*


class MapFragment : Fragment() {
    private var mapDelegate: MapDelegate? = null

    private var listener: LocationListener? = null
    private var gpsEnabled = true
    var destination: com.google.maps.model.LatLng? = null
    var hideParkButton = false


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater!!.inflate(R.layout.fragment_map, container, false)
        view.mapView.onCreate(savedInstanceState)
        mapDelegate = MapDelegate(activity, destination)
        view.mapView.getMapAsync(mapDelegate)
        setupPermission()
        view.parkButton.setOnClickListener { parkCar() }


        if (hideParkButton) {
            view.parkButton.visibility = View.INVISIBLE
        }

        ParkModel.readFromFile(context)?.let { view.parkButton.text = context.getString(R.string.navToCar) }


        return view
    }


    private fun parkCar() {

        val granted = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (granted && gpsEnabled) {
            if (ParkModel.readFromFile(context) != null) {
                val parkModel = ParkModel.readFromFile(context)!!

                mapDelegate!!.ctrl.route(mapDelegate!!.getMap(), com.google.maps.model.LatLng(parkModel.latitude, parkModel.longitude))
                view!!.parkButton.text = context.getString(R.string.parkCar)
                mapDelegate!!.parkMarker?.let { marker: Marker -> marker.remove() }
            } else {
                ParkingController(mapDelegate!!, context).parkCar()
                view!!.parkButton.text = context.getString(R.string.navToCar)
            }
        } else {
            Log.i("Permission", "Permission will be requested")
            startGpsListener()

        }


    }


    private fun startGpsListener() {


        val lm = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        listener = object : LocationListener {
            override fun onLocationChanged(p0: Location?) = Unit
            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?){
                Toast.makeText(activity, R.string.gpsStatusChange, Toast.LENGTH_SHORT)
            }
            override fun onProviderEnabled(p0: String?) {
                // view!!.parkButton.alpha = 1f
                // view!!.parkButton.isEnabled = true
                gpsEnabled = true
                view!!.mapView.getMapAsync(mapDelegate)
            }

            override fun onProviderDisabled(p0: String?) {
                // view!!.parkButton.alpha = 0.5f
                // view!!.parkButton.isEnabled = false
                Toast.makeText(context, R.string.activateGPS, Toast.LENGTH_LONG)
                startActivity( Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                gpsEnabled = false
            }


        }
        val granted = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (granted) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)

            Log.i("STATUS", "Listener rigistered")
        } else {
            gpsEnabled = false
            setupPermission()
        }
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
        if (listener == null) {
            startGpsListener()
        }

    }



    fun setupPermission() {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                val builder = AlertDialog.Builder(context)
                Log.i("Permission", "Permission alert")
                builder.setMessage(R.string.gpsPermissionInfo)
                        .setTitle(R.string.permissionRequired)

                builder.setPositiveButton("OK"
                ) { _, _ ->
                    Log.i("ALERT", "Clicked")
                    makeRequest()
                }

                val dialog = builder.create()
                dialog.show()
            } else {
                Log.i("Permission", "Permission Request")
                makeRequest()


        }
    }

    fun makeRequest() = ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 101)

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
                        startGpsListener()
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
}
