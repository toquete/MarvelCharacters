package com.guilherme.marvelcharacters.ui.comics

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.data.model.Comic

class ComicsAdapter(private val comicsList: List<Comic>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comics_item_list, parent, false)

        return BindingHolder(view)
    }

    override fun getItemCount() = comicsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BindingHolder).bind(comicsList[position].title, comicsList[position].description)
    }

    inner class BindingHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val comicTitle = item.findViewById<TextView>(R.id.textview_title)
        private val comicDescription = item.findViewById<TextView>(R.id.textview_description)

        fun bind(title: String?, description: String?) {
            comicTitle.text = title
            comicDescription.text = description
        }
    }
}