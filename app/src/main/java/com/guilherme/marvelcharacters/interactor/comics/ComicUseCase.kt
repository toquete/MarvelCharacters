package com.guilherme.marvelcharacters.interactor.comics

import com.guilherme.marvelcharacters.data.model.Comic
import com.guilherme.marvelcharacters.data.repository.comic.ComicRepository
import com.guilherme.marvelcharacters.interactor.UseCase

class ComicUseCase(private val comicRepository: ComicRepository) : UseCase<List<Comic>>() {

    var id: Int = 0

    override suspend fun executeOnBackground(): List<Comic> {
        return comicRepository.getComics(id)
    }
}