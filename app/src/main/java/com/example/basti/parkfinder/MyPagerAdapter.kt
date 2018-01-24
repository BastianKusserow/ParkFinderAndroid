package com.example.basti.parkfinder

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager

/**
 * Created by Basti on 31.10.17.
 */
class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm), ViewPager.OnPageChangeListener {

    lateinit var bottomNavigationView: BottomNavigationView

    override fun onPageScrollStateChanged(state: Int) = Unit

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
    override fun onPageSelected(position: Int) {
        val menu = bottomNavigationView.menu
        bottomNavigationView.selectedItemId = menu.getItem(position).itemId
    }


    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return LotTableFragment.newInstance()
            1 -> return MapFragment.newInstance()
        }

        return LotTableFragment.newInstance()
    }

    override fun getCount(): Int {
        return 2
    }


}