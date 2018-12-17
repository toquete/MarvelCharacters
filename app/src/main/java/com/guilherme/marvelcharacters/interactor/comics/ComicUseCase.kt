package com.guilherme.marvelcharacters.interactor.comics

import com.guilherme.marvelcharacters.data.model.Comic
import com.guilherme.marvelcharacters.data.repository.comic.ComicRepository
import com.guilherme.marvelcharacters.interactor.UseCase

class ComicUseCase(private val comicRepository: ComicRepository) : UseCase<Int, List<Comic>>() {

    override suspend fun executeOnBackground(parameters: Int): List<Comic> {
        return comicRepository.getComics(parameters)
    }
}