package com.guilherme.marvelcharacters.remote

import com.guilherme.marvelcharacters.core.model.Character
import com.guilherme.marvelcharacters.remote.model.CharacterResponse
import com.guilherme.marvelcharacters.remote.model.toExternalModel
import com.guilherme.marvelcharacters.remote.service.Api
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import javax.inject.Inject

internal class CharacterRemoteDataSourceImpl @Inject constructor(
    private val api: Api
) : CharacterRemoteDataSource {

    override suspend fun getCharacters(
        name: String,
        key: String,
        privateKey: String
    ): List<Character> {
        // TODO: mover regra para use case
        val (ts, hash) = getKeys(privateKey, key)

        return api.getCharacters(ts, hash, key, name)
            .container
            .results
            .map(CharacterResponse::toExternalModel)
    }

    override suspend fun getCharacterById(id: Int, key: String, privateKey: String): Character {
        val (ts, hash) = getKeys(privateKey, key)

        return api.getCharacterById(id, ts, hash, key)
            .container
            .results
            .first()
            .toExternalModel()
    }

    private fun getKeys(
        privateKey: String,
        key: String
    ): Pair<String, String> {
        val ts = System.currentTimeMillis().toString()
        val hash = String(Hex.encodeHex(DigestUtils.md5(ts + privateKey + key)))
        return Pair(ts, hash)
    }
}