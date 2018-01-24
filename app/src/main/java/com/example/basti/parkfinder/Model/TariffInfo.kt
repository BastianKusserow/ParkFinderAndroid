package com.example.basti.parkfinder.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by maxis on 24.01.2018.
 */
data class TariffInfo (
        @SerializedName("tariffPaymentOptions") val tariffPaymentOptions : String,
        @SerializedName("tariffMaxParkingTime") val maxParkingTime : String
)