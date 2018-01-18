package com.example.basti.parkfinder.Controller

import android.content.Context
import android.util.Log
import com.example.basti.parkfinder.Model.LotItem
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.DirectionsApiRequest
import com.google.maps.GeoApiContext
import com.google.maps.model.LatLng
import com.google.maps.model.TravelMode

/**
 * Created by Basti on 13.01.18.
 */
class MapController(val context: Context) {

    fun calculateColor(lot: LotItem): Float {
        var resultColor = BitmapDescriptorFactory.HUE_AZURE
        lot.details?.let {
            val capacity = it.allocation.capacity?.toDoubleOrNull()
            val stellplaetze = lot.stellplaetze.toDouble()
            var percentage = 0.0
            capacity?.let { percentage = (capacity / stellplaetze) * 100 }
            if (percentage in 0..25) {
                resultColor = BitmapDescriptorFactory.HUE_GREEN
            } else if (percentage in 26..50) {
                resultColor = BitmapDescriptorFactory.HUE_YELLOW
            } else if (percentage in 51..75) {
                resultColor = BitmapDescriptorFactory.HUE_ORANGE
            } else if (percentage in 76..100) {
                resultColor = BitmapDescriptorFactory.HUE_RED
            } else {
                return 0f
            }
        }
        return resultColor
    }


    fun calculateRoute() {
        var result = DirectionsApiRequest(GeoApiContext.Builder().apiKey("AIzaSyBeepv58KvUX5hltuToydwYNEQIr38zm6M").build()).origin(LatLng(50.3209984, 11.9406706)).destination(LatLng(50.3209984, 11.9406706))
                .mode(TravelMode.WALKING).optimizeWaypoints(true).await()
        Log.i("DIRECTIONS RESULT", result.toString())
    }
}