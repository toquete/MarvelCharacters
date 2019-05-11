package com.guilherme.marvelcharacters.data.repository.comic

import com.guilherme.marvelcharacters.data.model.Comic

interface ComicRepository {

    suspend fun getComics(characterId: Int): List<Comic>
}