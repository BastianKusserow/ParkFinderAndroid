package com.example.basti.parkfinder.Download

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.basti.parkfinder.Model.LotEntry
import com.example.basti.parkfinder.Model.LotModelSingleton
import com.example.basti.parkfinder.R
import com.example.basti.parkfinder.interfaces.IDBApi
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class JSONLoader(val context: Context) {

    private lateinit var retrofit: Retrofit

    init {
        initialize()
    }

    private fun initialize() {
        val gson = GsonBuilder().create()

        retrofit = Retrofit.Builder().baseUrl(context.resources.getString(R.string.baseURL_DBApi)).
                addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    fun downloadData() {

        val api = retrofit.create(IDBApi::class.java)

        val call = api.getLots()
        call.enqueue(object : Callback<LotEntry> {

            override fun onFailure(call: Call<LotEntry>?, t: Throwable?) {
                Log.d("####", "FAIL")
                displayAlert()
            }

            override fun onResponse(call: Call<LotEntry>?, response: Response<LotEntry>?) {

                if (response != null) {
                    LotModelSingleton.instance.lotData = response.body()!!
                    printInfos(response)
                }
            }
        })
    }

    private fun printInfos(response: Response<LotEntry>) {
        val data = response.body()!!
        val code = response.code()
        Log.d("Request Code: ", code.toString())
        Log.d("Request Body: ", data.toString())
    }

    private fun displayAlert() {
        val alert = AlertDialog.Builder(context)
        alert.setTitle("No Internet").setPositiveButton("OK", { dialogInterface, number ->
            val intent = Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
            context.startActivity(intent)
        }).create()
        alert.show()
    }
}