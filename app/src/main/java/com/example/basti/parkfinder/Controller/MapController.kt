package com.example.basti.parkfinder.Controller

import android.content.Context
import com.example.basti.parkfinder.Model.LotEntry
import com.example.basti.parkfinder.Model.LotItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by Basti on 13.01.18.
 */
class MapUtil(val context: Context) {

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

    fun populate(map: GoogleMap, list: LotEntry) {
        for (lot in list!!.lots) {
            var marker = map!!.addMarker(MarkerOptions()
                    .position(LatLng(lot.geoLocation.latitude, lot.geoLocation.longitude))
                    .title(lot.mLotName).snippet(lot.mAdress.street).icon(BitmapDescriptorFactory.defaultMarker(MapUtil(context).calculateColor(lot))))
            marker.tag = lot
        }
    }

}