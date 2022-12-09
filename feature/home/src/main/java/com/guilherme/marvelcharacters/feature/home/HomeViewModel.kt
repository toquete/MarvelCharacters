package com.guilherme.marvelcharacters.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilherme.marvelcharacters.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val uiState = _uiState.asStateFlow()

    var query: String? = null

    fun onSearchCharacter(character: String) {
        viewModelScope.launch {
            _uiState.update { HomeUiState.Loading }
            runCatching {
                getCharactersUseCase(
                    character,
                    key = "8f856183ec2966942d063a0a4b1401b0",
                    privateKey = "d6d7e23b91bbecd81fc237c078b8177b476b4f91"
                )
            }.onSuccess { list ->
                _uiState.update {
                    if (list.isEmpty()) {
                        HomeUiState.Error(R.string.empty_state_message)
                    } else {
                        HomeUiState.Success(list)
                    }
                }
            }.onFailure {
                // TODO: melhorar tratativa de erro
                _uiState.update { HomeUiState.Error(R.string.request_error_message) }
            }
        }
    }
}