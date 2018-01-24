package com.example.basti.parkfinder.Controller

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.support.v4.content.ContextCompat
import android.util.Log
import com.example.basti.parkfinder.Model.ParkModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.DirectionsApiRequest
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.model.DirectionsResult
import com.google.maps.model.LatLng
import com.google.maps.model.TravelMode

/**
 * Created by Basti on 18.01.18.
 */
class RoutingController(val context: Context, val supervisor: RouteCalculationListener?) {

    private var map: GoogleMap? = null


    fun route(map: GoogleMap, to: LatLng) {
        this.map = map

        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener {
                it?.let {
                    calculateRoute(com.google.maps.model.LatLng(it.latitude, it.longitude),
                            com.google.maps.model.LatLng(to.lat, to.lng), TravelMode.DRIVING)
                    ParkModel.deleteFile(context)
                }

            }
        }


    }

    private fun calculateRoute(from: LatLng, to: LatLng, mode: TravelMode) {

        var result = DirectionsApiRequest(GeoApiContext.Builder().apiKey("AIzaSyBeepv58KvUX5hltuToydwYNEQIr38zm6M")
                .build()).origin(from)
                .destination(to)
                .mode(mode).setCallback(object : PendingResult.Callback<DirectionsResult> {
            override fun onFailure(e: Throwable?) {
                Log.d("#######", "OnFailureCalled: " + e!!.localizedMessage)
            }

            override fun onResult(result: DirectionsResult?) {
                Log.d("#######", "OnResultCalled: " + result!!.routes[0].legs[0].endAddress)
                val options = PolylineOptions()
                options.width(10f).color(Color.parseColor("#2196F3"))


                for (loc in result!!.routes[0].legs[0].steps) {
                    options.add(com.google.android.gms.maps.model.LatLng(loc.startLocation.lat, loc.startLocation.lng))
                    loc.polyline.decodePath().forEach({ l ->
                        options.add(com.google.android.gms.maps.model.LatLng(l.lat, l.lng))
                    })
                    options.add(com.google.android.gms.maps.model.LatLng(loc.endLocation.lat, loc.endLocation.lng))

                    // }
                }
                Log.i("DIRECTIONS RESULT", result.routes[0].overviewPolyline.encodedPath)
                //val polyline = map!!.addPolyline(options)
                supervisor?.onRouteCalculated(options)
            }

        })

        //return polyline
    }
}

interface RouteCalculationListener {
    fun onRouteCalculated(options: PolylineOptions)
}