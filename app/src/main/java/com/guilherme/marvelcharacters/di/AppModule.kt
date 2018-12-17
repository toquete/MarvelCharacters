package com.guilherme.marvelcharacters.di

import com.guilherme.marvelcharacters.data.repository.character.CharacterRepository
import com.guilherme.marvelcharacters.data.repository.character.CharacterRepositoryImpl
import com.guilherme.marvelcharacters.data.repository.comic.ComicRepository
import com.guilherme.marvelcharacters.data.repository.comic.ComicRepositoryImpl
import com.guilherme.marvelcharacters.data.source.remote.RetrofitFactory
import com.guilherme.marvelcharacters.interactor.characters.CharacterUseCase
import com.guilherme.marvelcharacters.interactor.comics.ComicUseCase
import com.guilherme.marvelcharacters.ui.comics.ComicsViewModel
import com.guilherme.marvelcharacters.ui.main.MainViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val module = module {

    single { RetrofitFactory.api }

    single<CharacterRepository> { CharacterRepositoryImpl(get()) }
    single<ComicRepository> { ComicRepositoryImpl(get()) }

    single { CharacterUseCase(get()) }
    single { ComicUseCase(get()) }

    viewModel { MainViewModel(get()) }
    viewModel { ComicsViewModel(get()) }

}