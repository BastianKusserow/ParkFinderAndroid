package com.example.basti.parkfinder.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by Basti on 09.11.17.
 */
data class DetailEntry(@SerializedName("allocations") val allocations: MutableList<Allocations>)