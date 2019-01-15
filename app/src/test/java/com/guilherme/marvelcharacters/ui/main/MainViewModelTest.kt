package com.guilherme.marvelcharacters.ui.main

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.guilherme.marvelcharacters.data.model.Character
import com.guilherme.marvelcharacters.infrastructure.value
import com.guilherme.marvelcharacters.interactor.characters.CharacterInteractor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var characterInteractor: CharacterInteractor

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        mainViewModel = MainViewModel(characterInteractor, Dispatchers.Unconfined)
    }

    @Test
    fun onSearchCharacter_listIsReturned() {
        val list = listOf(Character(0, "spider", "bla"))
        whenever(
            runBlocking { characterInteractor.execute("spider") }
        ) doReturn value(list)

        mainViewModel.onSearchCharacter("spider")

        assertEquals(CharacterViewState.ShowCharacters(list), mainViewModel.state.value)
    }

    @Test
    fun onSearchCharacter_emptyListIsReturned() {
        val list = emptyList<Character>()
        whenever(
            runBlocking { characterInteractor.execute("spider") }
        ) doReturn value(list)

        mainViewModel.onSearchCharacter("spider")

        assertEquals(CharacterViewState.EmptyList, mainViewModel.state.value)
    }

    @Ignore
    @Test
    fun onSearchCharacter_errorIsReturned() {
        whenever(
            runBlocking { characterInteractor.execute("spider") }
        ) doReturn error("error")

        mainViewModel.onSearchCharacter("spider")

        assertEquals(CharacterViewState.Error("error"), mainViewModel.state.value)
    }
}