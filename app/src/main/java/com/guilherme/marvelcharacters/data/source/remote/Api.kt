package com.guilherme.marvelcharacters.data.source.remote

import com.guilherme.marvelcharacters.data.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("characters")
    fun getCharacters(@Query("ts") ts: String,
                      @Query("hash") hash: String,
                      @Query("apikey") apiKey: String,
                      @Query("nameStartsWith") nameStartsWith: String) : Call<Result>

    // TODO: 1 - Implementar assinatura da nova API
}