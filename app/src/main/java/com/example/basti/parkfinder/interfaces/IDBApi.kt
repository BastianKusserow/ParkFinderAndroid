package com.example.basti.parkfinder.interfaces

import com.example.basti.parkfinder.Model.LotEntry
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface IDBApi {

    @Headers("Authorization: Bearer e62d821490f900a487a91192b64bde15")
    @GET("spaces")
    fun getLots(): Call<LotEntry>
}