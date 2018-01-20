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
        viewPager.adapter = adapter

        listener = MyNavItemSelectedListener(viewPager)
        bottomNav.setOnNavigationItemSelectedListener(listener)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        /*menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))*/
        return true
    }


}
