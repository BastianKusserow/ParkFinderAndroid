package com.example.basti.parkfinder

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by Basti on 31.10.17.
 */
class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
       return LotTableFragment.newInstance()
    }

    override fun getCount(): Int {
        return 2
    }
}