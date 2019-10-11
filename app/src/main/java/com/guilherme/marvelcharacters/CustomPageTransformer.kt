package com.guilherme.marvelcharacters

import android.view.View
import androidx.viewpager.widget.ViewPager

class CustomPageTransformer : ViewPager.PageTransformer {

    private var viewPager: ViewPager? = null

    override fun transformPage(view: View, position: Float) {
        if (viewPager == null) {
            viewPager = view.parent as ViewPager
        }
        view.scaleY = 1 - Math.abs(position)
        view.scaleX = 1 - Math.abs(position)
    }

}