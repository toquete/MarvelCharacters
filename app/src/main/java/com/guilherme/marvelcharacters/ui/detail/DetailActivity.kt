package com.guilherme.marvelcharacters.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.databinding.ActivityDetailBinding
import org.koin.android.ext.android.inject

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val args: DetailActivityArgs by navArgs()

    private val detailViewModel: DetailViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewBindings()
        setupObservers()
    }

    private fun setupObservers() {
        detailViewModel.state.observe(this, Observer { state ->
            when (state) {
                is DetailViewModel.DetailState.Success -> {
                    Snackbar.make(binding.collapsingToolbarLayout, "Success!", Snackbar.LENGTH_SHORT).show()
                }
                is DetailViewModel.DetailState.Error -> {
                    Snackbar.make(binding.collapsingToolbarLayout, state.error.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setupViewBindings() {
        binding.collapsingToolbarLayout.title = args.character.name
        binding.description.text = if (args.character.description.isEmpty()) {
            getString(R.string.no_description_available)
        } else {
            args.character.description
        }
        Glide.with(this)
            .load("${args.character.thumbnail.path}.${args.character.thumbnail.extension}")
            .centerCrop()
            .into(binding.imageView)

        binding.fab.setOnClickListener { detailViewModel.onFabClick(args.character) }
    }
}