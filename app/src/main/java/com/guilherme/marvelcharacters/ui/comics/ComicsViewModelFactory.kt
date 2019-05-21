package com.guilherme.marvelcharacters.ui.comics

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import kotlin.coroutines.CoroutineContext

// TODO: 3 - Injetar dependencias do view model
class ComicsViewModelFactory(
    private val coroutineContext: CoroutineContext
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ComicsViewModel(coroutineContext) as T
    }
}