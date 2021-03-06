package com.example.basti.parkfinder.Download

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.basti.parkfinder.Model.DetailEntry
import com.example.basti.parkfinder.Model.CarParkItems
import com.example.basti.parkfinder.Model.CarParktModelSingleton
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
        call.enqueue(object : Callback<CarParkItems> {

            override fun onFailure(call: Call<CarParkItems>?, t: Throwable?) {
                Log.d("####", "FAIL")
                displayAlert()
            }

            override fun onResponse(call: Call<CarParkItems>?, response: Response<CarParkItems>?) {

                if (response != null) {
                    CarParktModelSingleton.instance.lotData = response.body()!!
                    Log.d("DOWNLOAD", "Hauptdaten geladen")
                    printInfos(response)
                    downloadDetails()
                }
            }
        })
    }

    fun downloadDetails() {
        val api = retrofit.create(IDBApi::class.java)
        val call = api.getOccupations()
        call.enqueue(object : Callback<DetailEntry> {
            override fun onFailure(call: Call<DetailEntry>?, t: Throwable?) {
                Log.d("LALALA", "Onfailure")
            }

            override fun onResponse(call: Call<DetailEntry>?, response: Response<DetailEntry>?) {
                Log.d("LALALA", response!!.body().toString())

                response?.let { response ->

                    Log.d("DOWNLOAD", "Versuche die details zu setzen")
                    setDetails(response.body()!!)

                }
            }
        })
    }

    fun setDetails(details: DetailEntry) {
        val copy = CarParktModelSingleton.instance.lotData

        for (element in details.allocations) {
            var index = CarParktModelSingleton.instance.lotData.getElement(element.space.id)

            index?.let {
                copy.lots[index].details = element
            }


        }
        Log.d("DOWNLOAD", "List will be sorted and set")
        copy.lots.sortBy { it.details == null }
        CarParktModelSingleton.instance.lotData = copy
    }


    private fun printInfos(response: Response<CarParkItems>) {
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