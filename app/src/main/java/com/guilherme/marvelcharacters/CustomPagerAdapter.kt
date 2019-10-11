package com.guilherme.marvelcharacters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.page.view.*

class CustomPagerAdapter(
    private val context: Context,
    private val items: List<PagerItem>,
    private val onItemClickListener: (position: Int) -> Unit
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return items.count()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.page, null)
        items[position].run {
            view.month.text = month
            view.status.text = status
        }

        view.setOnClickListener { onItemClickListener(position) }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        view.setOnClickListener { onItemClickListener(position) }
        container.removeView(view)
    }
}