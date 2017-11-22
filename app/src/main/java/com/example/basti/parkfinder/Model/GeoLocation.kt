package com.example.basti.parkfinder.Model

import com.google.gson.annotations.SerializedName

data class GeoLocation(@SerializedName("longitude") val longitude: Double,
                       @SerializedName("latitude") val latitude: Double)

