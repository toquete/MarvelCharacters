package com.guilherme.marvelcharacters.ui.comics

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.guilherme.marvelcharacters.data.model.Comic
import com.guilherme.marvelcharacters.interactor.comics.ComicUseCase

class ComicsViewModel(private val comicUseCase: ComicUseCase) : ViewModel() {

    val isLoading = ObservableBoolean(false)

    val isEmpty = ObservableBoolean(true)

    val message = ObservableField<String>()

    private val _comics = MutableLiveData<List<Comic>>()

    val comics: LiveData<List<Comic>>
        get() = _comics

    override fun onCleared() {
        super.onCleared()
        comicUseCase.unsubscribe()
    }

    fun onStart(characterId: Int) {
        callGetComics(characterId)
    }

    private fun callGetComics(characterId: Int) {
        isLoading.set(true)
        isEmpty.set(false)

        with(comicUseCase) {
            id = characterId
            execute {
                onComplete {
                    _comics.value = it

                    if (it.isEmpty()) {
                        message.set("No comics available for this character :/")
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