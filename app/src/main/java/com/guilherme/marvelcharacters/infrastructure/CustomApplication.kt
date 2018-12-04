package com.guilherme.marvelcharacters.infrastructure

import android.app.Application
import com.guilherme.marvelcharacters.data.source.remote.RetrofitFactory

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RetrofitFactory.makeRetrofitService()
    }
}