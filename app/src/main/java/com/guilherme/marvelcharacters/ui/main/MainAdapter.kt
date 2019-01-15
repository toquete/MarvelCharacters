package com.guilherme.marvelcharacters.ui.main

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.data.model.Character
import com.guilherme.marvelcharacters.databinding.ItemListBinding

class MainAdapter(
    private var characters: List<Character>,
    private val onCharacterClick: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_list,
            parent,
            false
        )

        return BindingHolder(binding)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BindingHolder).bind(characters[position].id, characters[position].name)
    }

    inner class BindingHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(id: Int, character: String) {
            binding.character = character
            binding.textviewCharacter.setOnClickListener { onCharacterClick(id) }
            binding.executePendingBindings()
        }
    }
}