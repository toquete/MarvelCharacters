package com.guilherme.marvelcharacters.interactor

import com.guilherme.marvelcharacters.infrastructure.Either
import com.guilherme.marvelcharacters.infrastructure.either
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class Interactor<in T, out R>(private val coroutineContext: CoroutineContext) {

    suspend fun execute(params: T): Either<Exception, R> = withContext(coroutineContext) {
        either { run(params) }
    }

    protected abstract fun run(params: T): R
}