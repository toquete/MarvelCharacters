package com.guilherme.marvelcharacters.ui.comics

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.data.model.Comic
import com.guilherme.marvelcharacters.data.repository.comic.ComicRepositoryImpl
import com.guilherme.marvelcharacters.data.source.remote.RetrofitFactory
import kotlinx.android.synthetic.main.activity_comics.*
import kotlinx.coroutines.Dispatchers

class ComicsActivity : AppCompatActivity() {

    lateinit var viewModel: ComicsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comics)

        val characterId = intent.getIntExtra("CHARACTER_ID", 0)

        val api = RetrofitFactory.api
        val comicsRepository = ComicRepositoryImpl(api)

        // TODO 9 - Prover dependências para ComicsViewModelFactory
        viewModel = ViewModelProviders.of(
            this,
            ComicsViewModelFactory(comicsRepository, Dispatchers.Main)
        ).get(ComicsViewModel::class.java)

        // TODO: 10 - observar resultado da chamada a API
        viewModel.states.observe(this, Observer { state ->
            when(state) {
                is ComicsViewModel.ComicsState.LoadingState -> showLoading()
                is ComicsViewModel.ComicsState.Comics -> showComics(state.comics)
                is ComicsViewModel.ComicsState.EmptyState -> showEmptyState()
                is ComicsViewModel.ComicsState.ErrorState -> showError(state.error)
            }
        })

        viewModel.onStart(characterId)
    }

    private fun showComics(list: List<Comic>) {
        hideLoading()
        with(recyclerview_comics) {
            layoutManager = LinearLayoutManager(context)
            adapter = ComicsAdapter(list)
            visibility = View.VISIBLE
        }
    }

    private fun showError(error: Exception) {
        hideLoading()
        textview_message.text = error.message
        textview_message.visibility = View.VISIBLE
    }

    private fun showLoading() {
        recyclerview_comics.visibility = View.GONE
        textview_message.visibility = View.GONE
        progressbar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressbar.visibility = View.GONE
    }

    private fun showEmptyState() {
        hideLoading()
        textview_message.text = applicationContext.getString(R.string.empty_state_comics_message)
        textview_message.visibility = View.VISIBLE
    }
}
