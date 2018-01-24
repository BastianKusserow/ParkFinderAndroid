package com.example.basti.parkfinder.Model

import com.google.gson.annotations.SerializedName

data class CarPark(@SerializedName("id") val mLotID: String,
                   @SerializedName("title") val mLotName: String,
                   @SerializedName("address") val mAdress: Address,
                   @SerializedName("openingHours") val openingHours: String,
                   @SerializedName("numberParkingPlaces") val stellplaetze: String,
                   @SerializedName("geoLocation") val geoLocation: GeoLocation,
                   @SerializedName("tariffInfo") val tariffInfo : TariffInfo,
                   @SerializedName("tariffPrices") val tariffPrices : ArrayList<Tariff>,
                   var details: Allocations?
)

