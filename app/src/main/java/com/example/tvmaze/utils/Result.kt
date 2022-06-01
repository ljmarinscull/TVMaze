package com.example.tvmaze.utils

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Resource<out T : Any> {

    object Loading: Resource<Nothing>()
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}