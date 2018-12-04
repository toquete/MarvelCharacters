package com.guilherme.marvelcharacters.ui.comics

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField

class ComicsViewModel() : ViewModel() {

    val isLoading = ObservableBoolean(false)

    val isEmpty = ObservableBoolean(true)

    val message = ObservableField<String>()

    // TODO: Criar live data que será observada pela view

    // TODO: Sobrescrever método onCleared para cancelar o subscribe do use case

    fun onStart(characterId: Int) {
        callGetComics(characterId)
    }

    private fun callGetComics(characterId: Int) {
        TODO("Utilizar use case (coroutine) para coletar o resultado da chamada à API")
    }
}