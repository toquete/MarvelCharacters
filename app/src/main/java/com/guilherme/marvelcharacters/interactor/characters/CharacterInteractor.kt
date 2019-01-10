package com.guilherme.marvelcharacters.interactor.characters

import com.guilherme.marvelcharacters.BuildConfig
import com.guilherme.marvelcharacters.data.model.Character
import com.guilherme.marvelcharacters.data.source.remote.Api
import com.guilherme.marvelcharacters.interactor.Interactor
import kotlinx.coroutines.Dispatchers
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class CharacterInteractor(
    private val api: Api,
    coroutineContext: CoroutineContext = Dispatchers.IO
) : Interactor<String, List<Character>>(coroutineContext) {

    @Throws(Exception::class)
    override fun run(params: String): List<Character> {
        val ts = System.currentTimeMillis().toString()
        val hash = String(Hex.encodeHex(DigestUtils.md5(ts + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_KEY)))

        val result = api.getCharacters(ts, hash, BuildConfig.MARVEL_KEY, params).execute()
        return result.body()?.container?.characters ?: emptyList()
    }
}