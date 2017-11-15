package com.example.basti.parkfinder.Model


import java.util.*

class LotModelSingleton private constructor() : Observable() {

    private object Holder {
        val INSTANCE = LotModelSingleton()
    }

    var lotData = LotEntry(mutableListOf())
        set(value) {
            field = value
            setChanged()
            notifyObservers()
        }

    companion object {
        val instance: LotModelSingleton by lazy { Holder.INSTANCE }
    }


    init {
        lotData = LotEntry(mutableListOf())
    }

}
