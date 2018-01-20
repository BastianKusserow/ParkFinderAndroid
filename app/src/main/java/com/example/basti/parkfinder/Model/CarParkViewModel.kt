package com.example.basti.parkfinder.Model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import java.util.*


class CarParkViewModel : ViewModel(), Observer {

    private var lotData: MutableLiveData<CarParkItems> = MutableLiveData()


    init {
        lotData.value = CarParktModelSingleton.instance.lotData
        CarParktModelSingleton.instance.addObserver(this)
    }

    override fun update(p0: Observable?, p1: Any?) {
        Log.d("OBSERVER", CarParktModelSingleton.instance.lotData.lots.size.toString())
        lotData.value = CarParktModelSingleton.instance.lotData
    }


    fun getLotData(): MutableLiveData<CarParkItems> {
        return lotData
    }


    fun getElement(at: Int): CarPark {
        return lotData.value!!.lots[at]
    }

    fun clear() {
        lotData = MutableLiveData()
    }
}