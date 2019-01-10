package com.guilherme.marvelcharacters

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseViewModel(uiDispatcher: CoroutineDispatcher) : ViewModel() {

    private val viewModelJob = Job()

    protected open val uiScope = CoroutineScope(uiDispatcher + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}