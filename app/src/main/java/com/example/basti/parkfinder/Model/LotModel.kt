package com.example.basti.parkfinder.Model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import java.util.*


class LotModel : ViewModel(), Observer {

    private var lotData: MutableLiveData<LotEntry> = MutableLiveData()


    init {
        lotData.value = LotModelSingleton.instance.lotData
        LotModelSingleton.instance.addObserver(this)
    }

    override fun update(p0: Observable?, p1: Any?) {
        Log.d("OBSERVER", LotModelSingleton.instance.lotData.lots.size.toString())
        lotData.value = LotModelSingleton.instance.lotData
    }


    fun getLotData(): MutableLiveData<LotEntry> {
        return lotData
    }


    fun getElement(at: Int): LotItem {
        return lotData.value!!.lots[at]
    }

    fun clear() {
        lotData = MutableLiveData()
    }
}