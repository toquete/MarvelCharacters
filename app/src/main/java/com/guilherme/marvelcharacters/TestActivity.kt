package com.guilherme.marvelcharacters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val list = listOf(
            PagerItem("janeiro", "paga"),
            PagerItem("fevereiro", "fechada"),
            PagerItem("março", "aberta")
        )

        customViewPager.adapter = CustomPagerAdapter(this, list) { position ->
            customViewPager.currentItem = position
        }
        customViewPager.setPageTransformer(false, CustomPageTransformer())
//        customViewPager.clipChildren = false
//        customViewPager.setFadingEdgeLength(0)
        customViewPager.offscreenPageLimit = list.count()
    }
}
