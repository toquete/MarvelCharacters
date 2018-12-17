package com.guilherme.marvelcharacters.interactor.characters

import com.guilherme.marvelcharacters.data.model.Character
import com.guilherme.marvelcharacters.data.repository.character.CharacterRepository
import com.guilherme.marvelcharacters.interactor.UseCase

class CharacterUseCase(private val characterRepository: CharacterRepository) : UseCase<String, List<Character>>() {

    override suspend fun executeOnBackground(parameters: String): List<Character> {
        return characterRepository.getCharacters(parameters)
    }
}