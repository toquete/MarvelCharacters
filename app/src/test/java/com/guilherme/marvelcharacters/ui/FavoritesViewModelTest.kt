package com.guilherme.marvelcharacters.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.guilherme.marvelcharacters.R
import com.guilherme.marvelcharacters.data.model.Character
import com.guilherme.marvelcharacters.data.model.Image
import com.guilherme.marvelcharacters.data.repository.CharacterRepository
import com.guilherme.marvelcharacters.infrastructure.TestCoroutineRule
import com.guilherme.marvelcharacters.ui.favorites.FavoritesViewModel
import com.guilherme.marvelcharacters.util.getOrAwaitValue
import com.guilherme.marvelcharacters.util.observeForTesting
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoritesViewModelTest {

    lateinit var favoritesViewModel: FavoritesViewModel

    @RelaxedMockK
    lateinit var characterRepository: CharacterRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        favoritesViewModel = FavoritesViewModel(characterRepository, testCoroutineRule.testCoroutineDispatcher)
    }

    @Test
    fun `deleteCharacter - verifica se repositório foi chamado`() = testCoroutineRule.runBlockingTest {
        val character = Character(0, "Spider-Man", "The Amazing Spider-Man", Image("", ""))

        favoritesViewModel.deleteCharacter(character)

        coVerify { characterRepository.deleteFavoriteCharacter(character) }
    }

    @Test
    fun `onDeleteAllClick - envia mensagem de sucesso`() = testCoroutineRule.runBlockingTest {
        favoritesViewModel.snackbarMessage.observeForTesting {
            favoritesViewModel.onDeleteAllClick()

            coVerify { characterRepository.deleteAllFavoriteCharacters() }
            assertThat(favoritesViewModel.snackbarMessage.getOrAwaitValue().peekContent()).isEqualTo(R.string.character_deleted)
        }
    }

    @Test
    fun `onDeleteAllClick - envia mensagem de erro`() = testCoroutineRule.runBlockingTest {
        coEvery { characterRepository.deleteAllFavoriteCharacters() } throws Exception("This is an error")

        favoritesViewModel.snackbarMessage.observeForTesting {
            favoritesViewModel.onDeleteAllClick()

            coVerify { characterRepository.deleteAllFavoriteCharacters() }
            assertThat(favoritesViewModel.snackbarMessage.getOrAwaitValue().peekContent()).isEqualTo(R.string.error_message)
        }
    }

    @Test
    fun `onFavoriteItemClick - envia personagem para tela de detalhe`() {
        val character = Character(0, "Spider-Man", "The Amazing Spider-Man", Image("", ""))

        favoritesViewModel.navigateToDetail.observeForTesting {
            favoritesViewModel.onFavoriteItemClick(character)

            assertThat(favoritesViewModel.navigateToDetail.getOrAwaitValue().peekContent()).isEqualTo(character)
        }
    }

    @Test
    fun `init - envia lista de favoritos`() {
        val character = Character(0, "Spider-Man", "The Amazing Spider-Man", Image("", ""))
        val characterList = listOf(character)

        coEvery { characterRepository.getFavoriteCharacters() } returns MutableLiveData(characterList)

        val viewModel = FavoritesViewModel(characterRepository, testCoroutineRule.testCoroutineDispatcher)

        viewModel.list.observeForTesting {
            assertThat(viewModel.list.getOrAwaitValue()).isEqualTo(characterList)
        }
    }
}