package com.example.basti.parkfinder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : MyPagerAdapter
    private lateinit var listener : MyNavItemSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MyPagerAdapter(supportFragmentManager)
        adapter.bottomNavigationView = bottomNav
        viewPager.addOnPageChangeListener(adapter)
        viewPager.adapter = adapter

        listener = MyNavItemSelectedListener(viewPager)
        bottomNav.setOnNavigationItemSelectedListener(listener)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }


}
