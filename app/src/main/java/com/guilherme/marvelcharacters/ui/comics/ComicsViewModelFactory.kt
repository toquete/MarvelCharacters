package com.guilherme.marvelcharacters.ui.comics

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.guilherme.marvelcharacters.interactor.comics.ComicUseCase

class ComicsViewModelFactory(private val comicUseCase: ComicUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ComicsViewModel(comicUseCase) as T
    }
}