package com.example.basti.parkfinder.Model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import java.util.*


class LotModel : ViewModel(), Observer {

    private var lotData: MutableLiveData<MutableList<LotEntry>> = MutableLiveData()


    init {
        lotData.value = LotModelSingleton.instance.lotData
        LotModelSingleton.instance.addObserver(this)
    }

    override fun update(p0: Observable?, p1: Any?) {
        lotData.value = LotModelSingleton.instance.lotData
    }


    fun getLotData(): MutableLiveData<MutableList<LotEntry>> {
        return lotData
    }


    fun getElement(at: Int): LotEntry {
        return lotData.value!![at]
    }

    fun clear() {
        lotData = MutableLiveData()
    }
}