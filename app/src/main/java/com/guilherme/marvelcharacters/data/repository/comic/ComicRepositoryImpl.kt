package com.guilherme.marvelcharacters.data.repository.comic

import com.guilherme.marvelcharacters.BuildConfig
import com.guilherme.marvelcharacters.data.model.Comic
import com.guilherme.marvelcharacters.data.source.remote.Api
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils

class ComicRepositoryImpl(private val api: Api) : ComicRepository {

    override suspend fun getComics(characterId: Int): List<Comic> {
        val ts = System.currentTimeMillis().toString()
        val hash = String(Hex.encodeHex(DigestUtils.md5(ts + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_KEY)))

        TODO("1 - Implementar chamada à api")
    }
}