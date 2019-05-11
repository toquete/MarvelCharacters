package com.guilherme.marvelcharacters

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.guilherme.marvelcharacters.data.model.Comic
import com.guilherme.marvelcharacters.data.repository.comic.ComicRepository
import com.guilherme.marvelcharacters.ui.comics.ComicsViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class ComicsViewModelTest {

    lateinit var viewModel: ComicsViewModel

    @Mock
    lateinit var statesObserver: Observer<ComicsViewModel.ComicsState>

    @Mock
    lateinit var comicsRepository: ComicRepository

    @Captor
    lateinit var statesCaptor: ArgumentCaptor<ComicsViewModel.ComicsState>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = ComicsViewModel(comicsRepository, Dispatchers.Unconfined).apply {
            states.observeForever(statesObserver)
        }
    }

    @Test
    fun onStart_getComicsList_success() = runBlocking {
        val comic = Comic(1, "Comic Title", "Comic Description")
        val comicList = listOf(comic)

        whenever(comicsRepository.getComics(any())).thenReturn(comicList)

        viewModel.onStart(1)

        verify(statesObserver, times(2)).onChanged(statesCaptor.capture())

        with(statesCaptor.allValues) {
            assertEquals(2, size)
            assertEquals(ComicsViewModel.ComicsState.LoadingState, this[0])
            assertEquals(ComicsViewModel.ComicsState.Comics(comicList), this[1])
        }
    }

    @Test
    fun onStart_getComicsList_emptyList() = runBlocking {
        val comicList = emptyList<Comic>()

        whenever(comicsRepository.getComics(any())).thenReturn(comicList)

        viewModel.onStart(1)

        verify(statesObserver, times(2)).onChanged(statesCaptor.capture())

        with(statesCaptor.allValues) {
            assertEquals(2, size)
            assertEquals(ComicsViewModel.ComicsState.LoadingState, this[0])
            assertEquals(ComicsViewModel.ComicsState.EmptyState, this[1])
        }
    }

    @Test
    fun onStart_getComicsList_error() = runBlocking {
        val exception = Exception("This is an error")

        whenever(comicsRepository.getComics(any())).then { throw exception }

        viewModel.onStart(1)

        verify(statesObserver, times(2)).onChanged(statesCaptor.capture())

        with(statesCaptor.allValues) {
            assertEquals(2, size)
            assertEquals(ComicsViewModel.ComicsState.LoadingState, this[0])
            assertEquals(ComicsViewModel.ComicsState.ErrorState(exception), this[1])
        }
    }
}