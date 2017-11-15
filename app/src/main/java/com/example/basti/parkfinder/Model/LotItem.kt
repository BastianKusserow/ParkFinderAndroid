package com.example.basti.parkfinder.Model

import com.google.gson.annotations.SerializedName

data class LotItem(@SerializedName("id") val mLotID: String,
                   @SerializedName("title") val mLotName: String,
                   @SerializedName("address") val mAdress: Address,
                   @SerializedName("openingHours") val openingHours: String,
                   @SerializedName("numberParkingPlaces") val stellplaetze: String,
                   @SerializedName("geoLocation") val geoLocation: GeoLocation) {
}