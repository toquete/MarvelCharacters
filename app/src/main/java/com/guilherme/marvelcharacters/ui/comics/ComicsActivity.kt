package com.guilherme.marvelcharacters.ui.comics

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.data.model.Comic
import kotlinx.android.synthetic.main.activity_comics.*

class ComicsActivity : AppCompatActivity() {

    lateinit var viewModel: ComicsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comics)

        val characterId = intent.getIntExtra("CHARACTER_ID", 0)

        // TODO: 3 - Criar useCase para comics!

        viewModel = ViewModelProviders.of(this).get(ComicsViewModel::class.java)

        // TODO: 8 - observar resultado da chamada a API

        viewModel.onStart(characterId)
    }

    private fun showComics(list: List<Comic>) {
        with(recyclerview_comics) {
            layoutManager = LinearLayoutManager(context)
            adapter = ComicsAdapter(list)
        }
    }
}
