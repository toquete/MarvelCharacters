package com.guilherme.marvelcharacters.data.repository.character

import com.guilherme.marvelcharacters.data.model.Character

interface CharacterRepository {

    suspend fun getCharacters(name: String): List<Character>
}