package com.guilherme.marvelcharacters.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.guilherme.marvelcharacters.interactor.characters.CharacterUseCase

class MainViewModelFactory(private val characterUseCase: CharacterUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(characterUseCase) as T
    }
}