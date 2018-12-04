package com.guilherme.marvelcharacters.interactor.characters

import com.guilherme.marvelcharacters.data.model.Character
import com.guilherme.marvelcharacters.data.repository.CharacterRepository
import com.guilherme.marvelcharacters.interactor.UseCase

class CharacterUseCase(private val characterRepository: CharacterRepository) : UseCase<List<Character>>() {

    //há uma melhor maneira para passar parametros pro usecase??
    lateinit var characterName: String

    override suspend fun executeOnBackground(): List<Character> {
        return characterRepository.getCharacters(characterName)
    }
}