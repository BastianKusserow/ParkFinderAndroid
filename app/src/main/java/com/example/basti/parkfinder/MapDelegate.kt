package com.example.basti.parkfinder

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.example.basti.parkfinder.Controller.MapUtil
import com.example.basti.parkfinder.Controller.RouteCalculationListener
import com.example.basti.parkfinder.Controller.RoutingController
import com.example.basti.parkfinder.Model.LotEntry
import com.example.basti.parkfinder.Model.LotItem
import com.example.basti.parkfinder.Model.LotModel
import com.example.basti.parkfinder.Model.ParkModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*

/**
 * Created by Basti on 13.01.18.
 */
class MapDelegate(val activity: FragmentActivity, val destination: com.google.maps.model.LatLng?) : OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, RouteCalculationListener {

    var ctrl: RoutingController = RoutingController(activity, this)

    override fun onRouteCalculated(options: PolylineOptions) {
        Handler(Looper.getMainLooper()).post(Runnable {
            Log.d("-------", "SupervisorCallback")
            this.polyline = map!!.addPolyline(options)
            //map!!.addPolyline(options)
        })

    }

    private var map: GoogleMap? = null
    var parkMarker: Marker? = null
    var polyline: Polyline? = null

    fun getMap(): GoogleMap {
        return map!!
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        val parkModel = ParkModel.readFromFile(activity)
        if (parkModel != null) {
            parkMarker = map!!.addMarker(MarkerOptions()
                    .position(LatLng(parkModel.latitude, parkModel.longitude))
                    .title("My Car").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)))
        }

        map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(50.32275, 11.92632), 15f))


        Log.i("LOCATION", "ONMAPREADY")
        val permission = ContextCompat.checkSelfPermission(activity.applicationContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            map!!.isMyLocationEnabled = true
        }

        map!!.uiSettings.isZoomControlsEnabled = true

        /*val lm = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER) && permission==PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(activity).lastLocation.addOnSuccessListener {
                if (it!=null)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15f))
            }
        }*/

        if (destination != null) {
            ctrl!!.route(map, destination)
            registerRerouteListener()
        }

        val model = ViewModelProviders.of(activity).get(LotModel::class.java)
        model.getLotData().observe(activity, Observer<LotEntry> { list ->
            MapUtil(activity).populate(map, list!!)
        })


        map.setOnInfoWindowClickListener(this)


    }


    fun registerRerouteListener() {
        val lm = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val listener = object : LocationListener {
            override fun onLocationChanged(p0: Location?) {
                /*p0?.let {

                    map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(p0!!.latitude, p0!!.longitude), map!!.cameraPosition.zoom))
                }*/

                if (destination != null) {
                    Toast.makeText(activity, "Location Changed, Rerouting", Toast.LENGTH_LONG).show()
                    ctrl.route(map!!, destination)
                    polyline?.remove()
                }
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) = Unit
            override fun onProviderEnabled(p0: String?) = Unit
            override fun onProviderDisabled(p0: String?) = Unit


        }
        val granted = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (granted) {
            Log.i("LISTENER", "registered reroute listener")
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 5f, listener)

        }
    }

    override fun onInfoWindowClick(p0: Marker?) {
        Log.i("PARKFINDER", "On Info window clicked")
        var fm = InfoFragment()
        fm.lotItem = p0!!.tag as LotItem
        fm.hideFab = true
        activity.supportFragmentManager.beginTransaction().replace(R.id.container, fm).addToBackStack(null).commit()
    }

}