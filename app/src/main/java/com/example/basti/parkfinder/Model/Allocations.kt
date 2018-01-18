package com.example.basti.parkfinder.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by Basti on 29.11.17.
 */
data class Allocations(@SerializedName("allocation") val allocation: Allocation, @SerializedName("space") val space: Space) {
}