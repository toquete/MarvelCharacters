package com.guilherme.marvelcharacters.infrastructure

sealed class Either<out E, out V> {
    data class Error<out E>(val error: E): Either<E, Nothing>()
    data class Value<out V>(val value: V): Either<Nothing, V>()
}

fun <V> value(value: V): Either<Nothing, V> = Either.Value(value)
fun <E> error(value: E): Either<E, Nothing> = Either.Error(value)

fun <V> either(action: () -> V): Either<Exception, V> =
    try { value(action()) } catch (e: Exception) { error(e) }

inline fun <E, V, A> Either<E, V>
        .fold(e: (E) -> A, v: (V) -> A): A = when(this) {
    is Either.Error -> e(this.error)
    is Either.Value -> v(this.value)
}