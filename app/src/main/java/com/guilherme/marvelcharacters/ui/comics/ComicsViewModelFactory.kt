package com.guilherme.marvelcharacters.ui.comics

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

// TODO: 4 - Injetar dependencias do view model
class ComicsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ComicsViewModel() as T
    }
}