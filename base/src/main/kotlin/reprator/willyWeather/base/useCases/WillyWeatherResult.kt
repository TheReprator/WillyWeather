package reprator.willyWeather.base.useCases

import java.lang.Exception

sealed class WillyWeatherResult<out T> {
    open fun get(): T? = null
}

data class Success<T>(val data: T, val responseModified: Boolean = true) : WillyWeatherResult<T>() {
    override fun get(): T = data
}

data class ErrorResult(
    val throwable: Throwable? = null,
    val message: String? = null,
    val bod: Exception? = null
) : WillyWeatherResult<Nothing>()