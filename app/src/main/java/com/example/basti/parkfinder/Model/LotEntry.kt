package com.example.basti.parkfinder.Model

/**
 * Created by Basti on 09.11.17.
 */
data class LotEntry(val mLotID: String,
                    val mLotName: String,
                    val mLotStreet: String,
                    val lotLongitude: String?,
                    val lotLatitude: String?,
                    val details: DetailEntry?,
                    val stellplaetze: String,
                    val oeffnungszeiten: String,
                    val tarif1MonatDauerparken: String?,
                    var tarif1Std: String?,
                    val tarif1Tag: String?,
                    val tarif1Woche: String?,
                    val tarif30min: String?,
                    val maxParkdauer: String?,
                    val zahlung: String?) {

}