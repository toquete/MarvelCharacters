package com.guilherme.marvelcharacters.ui.comics

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.guilherme.marvelcharacters.data.repository.comic.ComicRepository
import kotlin.coroutines.CoroutineContext

// TODO: 4 - Injetar dependencias do view model
class ComicsViewModelFactory(
    private val comicRepository: ComicRepository,
    private val coroutineContext: CoroutineContext
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ComicsViewModel(comicRepository, coroutineContext) as T
    }
}