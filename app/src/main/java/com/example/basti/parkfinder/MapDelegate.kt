package com.example.basti.parkfinder

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.util.Log
import com.example.basti.parkfinder.Controller.MapController
import com.example.basti.parkfinder.Model.LotEntry
import com.example.basti.parkfinder.Model.LotModel
import com.example.basti.parkfinder.Model.ParkModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by Basti on 13.01.18.
 */
class MapDelegate(val activity: FragmentActivity) : OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private var map: GoogleMap? = null

    fun getMap(): GoogleMap {
        return map!!
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        val parkModel = ParkModel.readFromFile(activity)
        if (parkModel != null) {
            map!!.addMarker(MarkerOptions()
                    .position(LatLng(parkModel.latitude, parkModel.longitude))
                    .title("My Car").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)))
        }

        Log.i("LOCATION", "ONMAPREADY")
        val permission = ContextCompat.checkSelfPermission(activity.applicationContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            map!!.isMyLocationEnabled = true
        }

        map!!.uiSettings.isZoomControlsEnabled = true


        LocationServices.getFusedLocationProviderClient(activity).lastLocation.addOnSuccessListener {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15f))
        }


        val model = ViewModelProviders.of(activity).get(LotModel::class.java)
        model.getLotData().observe(activity, Observer<LotEntry> { list ->
            for (lot in list!!.lots) {
                var marker = map!!.addMarker(MarkerOptions()
                        .position(LatLng(lot.geoLocation.latitude, lot.geoLocation.longitude))
                        .title(lot.mLotName).snippet(lot.mAdress.street).icon(BitmapDescriptorFactory.defaultMarker(MapController(activity).calculateColor(lot))))
                marker.tag = lot
            }
        })

        MapController(activity).calculateRoute()

        map!!.setOnInfoWindowClickListener(this)

    }

    override fun onInfoWindowClick(p0: Marker?) {
        Log.i("PARKFINDER", "On Info window clicked")
    }

}