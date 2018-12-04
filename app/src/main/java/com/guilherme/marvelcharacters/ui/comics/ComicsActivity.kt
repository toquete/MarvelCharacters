package com.guilherme.marvelcharacters.ui.comics

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.data.model.Comic
import com.guilherme.marvelcharacters.data.repository.comic.ComicRepositoryImpl
import com.guilherme.marvelcharacters.data.source.remote.RetrofitFactory
import com.guilherme.marvelcharacters.databinding.ActivityComicsBinding
import com.guilherme.marvelcharacters.interactor.comics.ComicUseCase

class ComicsActivity : AppCompatActivity() {

    lateinit var binding: ActivityComicsBinding

    lateinit var viewModel: ComicsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val characterId = intent.getIntExtra("CHARACTER_ID", 0)

        val api = RetrofitFactory.api
        val repository = ComicRepositoryImpl(api)
        val userCase = ComicUseCase(repository)

        viewModel = ViewModelProviders.of(this, ComicsViewModelFactory(userCase)).get(ComicsViewModel::class.java)
        viewModel.comics.observe(this, Observer { result ->
            result?.let { showComics(it) }
        })

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
