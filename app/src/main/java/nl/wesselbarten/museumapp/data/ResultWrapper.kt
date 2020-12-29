package nl.wesselbarten.museumapp.data

import java.lang.Exception

/**
 * Adapted from https://github.com/android/architecture-samples.
 *
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ResultWrapper<out R> {

    data class Success<out T>(val data: T) : ResultWrapper<T>()

    data class Error(val exception: Exception) : ResultWrapper<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}