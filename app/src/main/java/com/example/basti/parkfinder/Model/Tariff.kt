package com.example.basti.parkfinder.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by maxis on 24.01.2018.
 */
data class Tariff (
        @SerializedName("duration") val duration : String,
        @SerializedName("price") val price : Double?
)