package com.guilherme.marvelcharacters.ui.comics

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.data.model.Comic
import com.guilherme.marvelcharacters.databinding.ComicsItemListBinding

class ComicsAdapter(private val comicsList: List<Comic>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ComicsItemListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.comics_item_list,
            parent,
            false
        )

        return BindingHolder(binding)
    }

    override fun getItemCount() = comicsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BindingHolder).bind(comicsList[position].title, comicsList[position].description)
    }

    inner class BindingHolder(private val binding: ComicsItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String?, description: String?) {
            binding.title = title
            binding.description = description
            binding.executePendingBindings()
        }
    }
}