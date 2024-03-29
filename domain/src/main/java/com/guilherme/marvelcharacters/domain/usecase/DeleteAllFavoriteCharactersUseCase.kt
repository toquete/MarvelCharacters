package com.guilherme.marvelcharacters.domain.usecase

import com.guilherme.marvelcharacters.data.repository.CharacterRepository
import javax.inject.Inject

class DeleteAllFavoriteCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke() {
        characterRepository.deleteAllFavoriteCharacters()
    }
}