package com.guilherme.marvelcharacters.ui.comics

import com.guilherme.marvelcharacters.BaseViewModel
import kotlin.coroutines.CoroutineContext

// TODO: 2 - Injetar dependências do ViewModel
class ComicsViewModel(
    coroutineContext: CoroutineContext
) : BaseViewModel(coroutineContext) {

    // TODO: 5 - Criar live data que será observada pela view

    // TODO: 6 - Sobrescrever uiScope

    fun onStart(characterId: Int) {
        callGetComics(characterId)
    }

    private fun callGetComics(characterId: Int) {
        TODO("7 - Utilizar uiScope (coroutine) para coletar o resultado da chamada à API")
    }

    // TODO: 4 - Criar sealed class com os estados da view
}