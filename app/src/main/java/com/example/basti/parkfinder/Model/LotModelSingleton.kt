package com.example.basti.parkfinder.Model

import java.util.*

class LotModelSingleton private constructor() : Observable() {

    private object Holder {
        val INSTANCE = LotModelSingleton()
    }

    var lotData = mutableListOf<LotEntry>()


    companion object {
        val instance: LotModelSingleton by lazy { Holder.INSTANCE }
    }


    init {
        lotData = mutableListOf()
        val lot1 = LotEntry("0", "Parkhaus Hof", "Hauptstraße", null, null, null, "100", "10", null, null, null, null, null, null, null)
        val lot2 = LotEntry("1", "Parkhaus 2 Hof", "Hauptstraße", null, null, null, "100", "10", null, null, null, null, null, null, null)
        lotData.add(lot1)
        lotData.add(lot2)
    }

    fun numberOfEntries(): Int {
        return lotData.size
    }

    fun add(element: LotEntry) {
        lotData.add(element)
        setChanged()
        notifyObservers()
    }


}
