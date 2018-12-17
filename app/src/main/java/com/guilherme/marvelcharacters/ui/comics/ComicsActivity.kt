package com.guilherme.marvelcharacters.ui.comics

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.data.model.Comic
import com.guilherme.marvelcharacters.databinding.ActivityComicsBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ComicsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComicsBinding

    private val comicsViewModel: ComicsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val characterId = intent.getIntExtra("CHARACTER_ID", 0)

        comicsViewModel.comics.observe(this, Observer { result ->
            result?.let { showComics(it) }
        })

        binding = DataBindingUtil.setContentView(this, R.layout.activity_comics)
        binding.viewModel = comicsViewModel

        comicsViewModel.onStart(characterId)
    }

    private fun showComics(list: List<Comic>) {
        with(binding.recyclerviewComics) {
            layoutManager = LinearLayoutManager(context)
            adapter = ComicsAdapter(list)
        }
    }
}
