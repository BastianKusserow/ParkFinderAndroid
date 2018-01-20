package com.example.basti.parkfinder

import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.view.MenuItem

/**
 * Created by Basti on 31.10.17.
 */
class MyNavItemSelectedListener : BottomNavigationView.OnNavigationItemSelectedListener {

    private var vp : ViewPager? = null

    constructor(viewPager: ViewPager){
        vp = viewPager
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.Parkplaetze -> {
                vp!!.currentItem = 0
                return true
            }
            R.id.Karte -> {
                vp!!.currentItem = 1

                return true
            }
        }
        return false
    }
}