package com.guilherme.marvelcharacters.ui.main

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.data.model.Character
import com.guilherme.marvelcharacters.ui.comics.ComicsActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private const val CHARACTER_ID = "CHARACTER_ID"
    }

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.state.observe(this, Observer {
            when(it) {
                is CharacterViewState.Loading -> showLoading()
                is CharacterViewState.ShowCharacters -> showCharacters(it.list)
                is CharacterViewState.Error -> showError(it.message)
                is CharacterViewState.EmptyList -> showError(resources.getString(R.string.no_characters_message))
            }
        })

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
        toggleLoading(false)
        with(recyclerview_characters) {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(context)
            adapter = MainAdapter(list) { goToComicsScreen(it) }
        }
    }

    private fun goToComicsScreen(characterId: Int) {
        Intent(this, ComicsActivity::class.java).apply {
            putExtra(CHARACTER_ID, characterId)
            startActivity(this)
        }
    }

    private fun showError(message: String?) {
        toggleLoading(false)
        message?.let {
            textview_message.text = message
            textview_message.visibility = View.VISIBLE
        }
    }

    private fun toggleLoading(isVisible: Boolean) {
        progressbar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showLoading() {
        toggleLoading(true)
        textview_message.visibility = View.GONE
    }
}
