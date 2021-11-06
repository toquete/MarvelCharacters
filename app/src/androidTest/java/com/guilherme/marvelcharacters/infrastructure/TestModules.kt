package com.guilherme.marvelcharacters.infrastructure

import android.content.Context
import androidx.room.Room
import com.guilherme.marvelcharacters.data.repository.CharacterRepository
import com.guilherme.marvelcharacters.data.repository.PreferenceRepository
import com.guilherme.marvelcharacters.data.service.Api
import com.guilherme.marvelcharacters.data.source.local.CharacterLocalDataSource
import com.guilherme.marvelcharacters.data.source.local.CharacterLocalDataSourceImpl
import com.guilherme.marvelcharacters.data.source.remote.CharacterRemoteDataSource
import com.guilherme.marvelcharacters.data.source.remote.CharacterRemoteDataSourceImpl
import com.guilherme.marvelcharacters.domain.model.Character
import com.guilherme.marvelcharacters.infrastructure.database.CharacterDatabase
import com.guilherme.marvelcharacters.ui.detail.DetailViewModel
import com.guilherme.marvelcharacters.ui.favorites.FavoritesViewModel
import com.guilherme.marvelcharacters.ui.home.HomeViewModel
import io.mockk.mockk
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DEFAULT_PREFERENCES = "default_preferences"

val testModule = module {
    single { mockk<Api>() }
    single { Room.inMemoryDatabaseBuilder(get(), CharacterDatabase::class.java).build() }
    single { get<CharacterDatabase>().characterDao() }
    factory<CharacterRemoteDataSource> { CharacterRemoteDataSourceImpl(get()) }
    factory<CharacterLocalDataSource> { CharacterLocalDataSourceImpl(get()) }
    single { CharacterRepository(get(), get()) }
    single { get<Context>().getSharedPreferences(DEFAULT_PREFERENCES, Context.MODE_PRIVATE) }
    single { PreferenceRepository(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { (character: Character) -> DetailViewModel(character, get()) }
}