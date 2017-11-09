package com.example.basti.parkfinder.Model

/**
 * Created by Basti on 09.11.17.
 */
data class DetailEntry(val lotID : Int,
                       /// Category which describes the actual free parking Lots.
                       val category : Int,
                       /// Category as a String.
                       val categoryText : String,
                       /// The actual Capacity of the parking lot.
                       val capacity : Double) {
}