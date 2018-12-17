package com.guilherme.marvelcharacters.infrastructure

import android.app.Application
import com.guilherme.marvelcharacters.data.source.remote.RetrofitFactory
import com.guilherme.marvelcharacters.di.module
import org.koin.android.ext.android.startKoin

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RetrofitFactory.makeRetrofitService()
        startKoin(this, listOf(module))
    }
}