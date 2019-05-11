package com.guilherme.marvelcharacters.ui.comics

import android.arch.lifecycle.ViewModel

class ComicsViewModel : ViewModel() {

    // TODO: 5- Criar live data que será observada pela view

    // TODO: 6 - Sobrescrever método onCleared para cancelar o subscribe do use case

    fun onStart(characterId: Int) {
        callGetComics(characterId)
    }

    private fun callGetComics(characterId: Int) {
        TODO("7 - Utilizar use case (coroutine) para coletar o resultado da chamada à API")
    }
}