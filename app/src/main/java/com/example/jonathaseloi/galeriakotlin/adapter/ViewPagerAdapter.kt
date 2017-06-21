package com.example.jonathaseloi.galeriakotlin.adapter

import android.content.Context
import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.jonathaseloi.galeriakotlin.R
import com.example.jonathaseloi.galeriakotlin.fragment.GaleriaFragment

class ViewPagerAdapter(private val context: Context, manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val titles = arrayOf("Imagens", "Videos", "Outros")

    companion object {

        private val GALERIA_IMAGENS = 0
        private val GALERIA_VIDEOS = 1
        private val GALERIA_OUTROS = 2
    }

    @UiThread
    override fun getItem(position: Int): Fragment? {
        when (position) {
            GALERIA_IMAGENS -> return GaleriaFragment.newInstance("Imagens", arrayOf("%/AppGallery/Imagens/%"),
                    R.layout.fragment_galeria_viewpager_imagens, R.id.recycler_view_imagens)
            GALERIA_VIDEOS -> return GaleriaFragment.newInstance("Videos", arrayOf("%/AppGallery/Videos/%"),
                    R.layout.fragment_galeria_viewpager_videos, R.id.recycler_view_videos)
            GALERIA_OUTROS -> return GaleriaFragment.newInstance("Outros", arrayOf("%/AppGallery/Outros/%"),
                    R.layout.fragment_galeria_viewpager_outros, R.id.recycler_view_outros)
        }
        return null
    }

    override fun getCount(): Int {
        return titles.size
    }

    @UiThread
    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

}
