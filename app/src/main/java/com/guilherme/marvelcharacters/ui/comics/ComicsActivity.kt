package com.guilherme.marvelcharacters.ui.comics

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.data.model.Comic
import com.guilherme.marvelcharacters.databinding.ActivityComicsBinding

class ComicsActivity : AppCompatActivity() {

    lateinit var binding: ActivityComicsBinding

    lateinit var viewModel: ComicsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val characterId = intent.getIntExtra("CHARACTER_ID", 0)

        // TODO: 3 - Criar useCase para comics!

        viewModel = ViewModelProviders.of(this).get(ComicsViewModel::class.java)

        // TODO: 8 - observar resultado da chamada a API

        binding = DataBindingUtil.setContentView(this, R.layout.activity_comics)
        binding.viewModel = viewModel

        viewModel.onStart(characterId)
    }

    private fun showComics(list: List<Comic>) {
        with(binding.recyclerviewComics) {
            layoutManager = LinearLayoutManager(context)
            adapter = ComicsAdapter(list)
        }
    }
}
