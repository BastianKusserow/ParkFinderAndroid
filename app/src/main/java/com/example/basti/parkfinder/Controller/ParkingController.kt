package com.example.basti.parkfinder.Controller

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.example.basti.parkfinder.MapDelegate
import com.example.basti.parkfinder.Model.ParkModel
import com.example.basti.parkfinder.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by Basti on 18.01.18.
 */
class ParkingController(val mapDelegate: MapDelegate, val context: Context) {


    fun parkCar() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener { location ->

                val markerOptions = MarkerOptions().title(context.getString(R.string.myCar)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                        .position(LatLng(location.latitude, location.longitude))

                mapDelegate!!.parkMarker = mapDelegate!!.getMap().addMarker(markerOptions)

                ParkModel(location.latitude, location.longitude).saveToFile(context)
            }
        }
    }
}