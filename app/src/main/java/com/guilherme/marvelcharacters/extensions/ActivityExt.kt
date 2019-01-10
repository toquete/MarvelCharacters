package com.guilherme.marvelcharacters.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity

inline fun <reified VM : ViewModel> FragmentActivity.provideViewModel(crossinline factory: () -> VM): VM =
    ViewModelProviders.of(this, object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = factory() as T
    }).get(VM::class.java)