package com.guilherme.marvelcharacters.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.guilherme.marvelcharacters.data.model.Character
import com.guilherme.marvelcharacters.interactor.characters.CharacterUseCase

class MainViewModel(private val characterUseCase: CharacterUseCase) : ViewModel() {

    val isLoading = ObservableBoolean(false)

    val isEmpty = ObservableBoolean(true)

    val message = ObservableField<String>("Use the search box above!")

    private val _characters = MutableLiveData<List<Character>>()

    val characters: LiveData<List<Character>>
        get() = _characters

    override fun onCleared() {
        super.onCleared()
        characterUseCase.unsubscribe()
    }

    fun onSearchCharacter(character: String) {
        isLoading.set(true)
        isEmpty.set(false)

        with(characterUseCase) {
            characterName = character
            execute {
                onComplete {
                    _characters.value = it

                    if (it.isEmpty()) {
                        message.set("No characters with that name. Try again!")
                        isEmpty.set(true)
                    }
                    isLoading.set(false)
                }

                onError {
                    message.set(it.message)
                    isEmpty.set(true)
                    isLoading.set(false)
                }
            }
        }
    }
}