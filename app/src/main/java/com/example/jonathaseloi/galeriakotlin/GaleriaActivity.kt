package com.example.jonathaseloi.galeriakotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import com.example.jonathaseloi.galeriakotlin.adapter.ViewPagerAdapter

import kotlinx.android.synthetic.main.activity_main.*

class GaleriaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar_galeria.setTitle("Galeria")
        setSupportActionBar(toolbar_galeria)

        setupViewPager()
    }

    private fun setupViewPager() {

        val adapter = ViewPagerAdapter(this, supportFragmentManager)
        viewpager_galeria.setOffscreenPageLimit(3)
        viewpager_galeria.setAdapter(adapter)

        tabs_galeria.setupWithViewPager(viewpager_galeria)
        tabs_galeria.setTabGravity(TabLayout.GRAVITY_CENTER)
        tabs_galeria.setTabMode(TabLayout.MODE_FIXED)

        appbar_galeria.setExpanded(true, true)
    }
}
