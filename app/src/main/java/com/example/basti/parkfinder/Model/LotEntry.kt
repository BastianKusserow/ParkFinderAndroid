package com.example.basti.parkfinder.Model

import com.google.gson.annotations.SerializedName
import java.util.*


class LotEntry(lots: MutableList<LotItem>) : Observable() {

    @SerializedName("items")
    var lots: MutableList<LotItem> = lots
        set(value) {
            field = value
            setChanged()
            notifyObservers()
        }

    fun getElement(id: String): Int? {

        val index = lots.indexOfFirst { it.mLotID == id }
        if (index >= 0) {
            return index
        }
        return null
    }
}

////@SerializedName("id") val mLotID: String,
////@SerializedName("title") val mLotName: String,
////@SerializedName("id") val mLotStreet: String,
////@SerializedName("id") val lotLongitude: String?,
////@SerializedName("id") val lotLatitude: String?,
//@SerializedName("id") val details: DetailEntry?,
////@SerializedName("id") val stellplaetze: String,
////@SerializedName("id") val oeffnungszeiten: String,
//@SerializedName("id") val tarif1MonatDauerparken: String?,
//@SerializedName("id") var tarif1Std: String?,
//@SerializedName("id") val tarif1Tag: String?,
//@SerializedName("id") val tarif1Woche: String?,
//@SerializedName("id") val tarif30min: String?,
//@SerializedName("id") val maxParkdauer: String?,
//@SerializedName("id") val zahlung: String?)