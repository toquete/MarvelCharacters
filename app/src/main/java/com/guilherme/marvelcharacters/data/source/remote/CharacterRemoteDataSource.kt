package com.guilherme.marvelcharacters.data.source.remote

import com.guilherme.marvelcharacters.data.model.CharacterData
import kotlinx.coroutines.flow.Flow

interface CharacterRemoteDataSource {

    fun getCharacters(name: String): Flow<List<CharacterData>>
}