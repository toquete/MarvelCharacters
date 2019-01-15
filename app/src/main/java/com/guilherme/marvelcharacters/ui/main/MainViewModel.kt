package com.guilherme.marvelcharacters.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.guilherme.marvelcharacters.BaseViewModel
import com.guilherme.marvelcharacters.data.model.Character
import com.guilherme.marvelcharacters.infrastructure.fold
import com.guilherme.marvelcharacters.interactor.characters.CharacterInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val characterInteractor: CharacterInteractor,
    uiDispatcher: CoroutineDispatcher = Dispatchers.Main
) : BaseViewModel(uiDispatcher) {

    private val _state = MutableLiveData<CharacterViewState>()

    val state: LiveData<CharacterViewState>
        get() = _state

    override val uiScope: CoroutineScope
        get() = super.uiScope

    fun onSearchCharacter(character: String) {
        uiScope.launch {
            _state.value = CharacterViewState.Loading
            characterInteractor.execute(character).fold({ error ->
                _state.value = CharacterViewState.Error(error.message)
            }, { response ->
                if (response.isEmpty()) {
                    _state.value = CharacterViewState.EmptyList
                } else {
                    _state.value = CharacterViewState.ShowCharacters(response)
                }
            })
        }
    }
}

sealed class CharacterViewState {
    data class ShowCharacters(val list: List<Character>) : CharacterViewState()
    object Loading : CharacterViewState()
    data class Error(val message: String?) : CharacterViewState()
    object EmptyList : CharacterViewState()
}