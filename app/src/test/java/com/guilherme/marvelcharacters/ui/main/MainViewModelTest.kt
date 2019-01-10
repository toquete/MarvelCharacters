package com.guilherme.marvelcharacters.ui.main

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.guilherme.marvelcharacters.data.model.Character
import com.guilherme.marvelcharacters.infrastructure.value
import com.guilherme.marvelcharacters.interactor.characters.CharacterInteractor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineContext
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var characterInteractor: CharacterInteractor

    lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        mainViewModel = MainViewModel(characterInteractor, Dispatchers.Unconfined)
    }

    @Test
    fun onSearchCharacter_useCaseIsCalled() {
        val list = listOf(Character(0, "spider", "bla"))
        whenever(
            runBlocking { characterInteractor.execute("spider") }
        ).thenReturn(value(list))

        mainViewModel.onSearchCharacter("spider")

        assertEquals(CharacterViewState.ShowCharacters(list), mainViewModel.state.value)
    }
}