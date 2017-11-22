package com.example.basti.parkfinder.Model

import com.google.gson.annotations.SerializedName

data class Address(@SerializedName("cityName") val city: String,
                   @SerializedName("street") val street: String) {
}

