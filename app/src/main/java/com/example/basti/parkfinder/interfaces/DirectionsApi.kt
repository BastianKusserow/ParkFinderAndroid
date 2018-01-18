package com.example.basti.parkfinder.interfaces

import com.example.basti.parkfinder.Model.LotEntry
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Basti on 17.01.18.
 */
interface DirectionsApi {

    @GET
    fun getPolylinePoints(): Call<LotEntry>
}