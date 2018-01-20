package com.example.basti.parkfinder.Model


import java.util.*

class CarParktModelSingleton private constructor() : Observable() {

    private object Holder {
        val INSTANCE = CarParktModelSingleton()
    }

    var lotData = CarParkItems(mutableListOf())
        set(value) {
            field = value
            setChanged()
            notifyObservers()

        }

    companion object {
        val instance: CarParktModelSingleton by lazy { Holder.INSTANCE }
    }


    init {
        lotData = CarParkItems(mutableListOf())
    }

}
