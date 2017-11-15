package com.example.basti.parkfinder.Model

import com.google.gson.annotations.SerializedName


data class LotEntry(@SerializedName("items") val lots: MutableList<LotItem>)

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