package com.guilherme.marvelcharacters.ui.comics

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.guilherme.marvelcharacters.BaseViewModel
import com.guilherme.marvelcharacters.data.model.Comic
import com.guilherme.marvelcharacters.data.repository.comic.ComicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

// TODO: 3 - Injetar dependências do ViewModel
class ComicsViewModel(
    private val comicsRepository: ComicRepository,
    coroutineContext: CoroutineContext
) : BaseViewModel(coroutineContext) {

    // TODO: 6 - Criar live data que será observada pela view
    private val _states = MutableLiveData<ComicsState>()
    val states: LiveData<ComicsState>
        get() = _states

    // TODO: 7 - Sobrescrever uiScope
    override val uiScope: CoroutineScope
        get() = super.uiScope

    fun onStart(characterId: Int) {
        callGetComics(characterId)
    }

    private fun callGetComics(characterId: Int) {
        // TODO("8 - Utilizar uiScope (coroutine) para coletar o resultado da chamada à API")
        uiScope.launch {
            _states.value = ComicsState.LoadingState
            try {
                val comicsList = comicsRepository.getComics(characterId)
                _states.value = if (comicsList.isEmpty()) {
                    ComicsState.EmptyState
                } else {
                    ComicsState.Comics(comicsList)
                }
            } catch (error: Exception) {
                _states.value = ComicsState.ErrorState(error)
            }
        }
    }

    // TODO: 5 - Criar sealed class com os estados da view
    sealed class ComicsState {
        data class Comics(val comics: List<Comic>) : ComicsState()
        data class ErrorState(val error: Exception) : ComicsState()
        object EmptyState : ComicsState()
        object LoadingState : ComicsState()
    }
}