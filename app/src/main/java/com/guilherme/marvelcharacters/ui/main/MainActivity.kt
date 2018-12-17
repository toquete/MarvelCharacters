package com.guilherme.marvelcharacters.ui.main

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.data.model.Character
import com.guilherme.marvelcharacters.data.repository.character.CharacterRepositoryImpl
import com.guilherme.marvelcharacters.data.source.remote.RetrofitFactory
import com.guilherme.marvelcharacters.databinding.ActivityMainBinding
import com.guilherme.marvelcharacters.interactor.characters.CharacterUseCase
import com.guilherme.marvelcharacters.ui.comics.ComicsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.characters.observe(this, Observer { result ->
            result?.let { showCharacters(result) }
        })

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainViewModel

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                mainViewModel.onSearchCharacter(query)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.search_bar)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }

    private fun showCharacters(list: List<Character>) {
        with(binding.recyclerviewCharacters) {
            layoutManager = LinearLayoutManager(context)
            adapter = MainAdapter(list) { goToComicsScreen(it) }
        }
    }

    private fun goToComicsScreen(characterId: Int) {
        Intent(this, ComicsActivity::class.java).apply {
            putExtra("CHARACTER_ID", characterId)
            startActivity(this)
        }
    }
}
