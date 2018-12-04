package com.guilherme.marvelcharacters.data.repository

import com.guilherme.marvelcharacters.data.model.Character

interface CharacterRepository {

    fun getCharacters(name: String): List<Character>
}