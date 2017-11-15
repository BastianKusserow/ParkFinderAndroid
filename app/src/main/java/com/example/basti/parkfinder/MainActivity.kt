package com.example.basti.parkfinder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : MyPagerAdapter
    private lateinit var listener : MyNavItemSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        listener = MyNavItemSelectedListener(viewPager)
        bottomNav.setOnNavigationItemSelectedListener(listener)


    }
}
