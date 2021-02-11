package reprator.willyWeather.base.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import reprator.willyWeather.base.useCases.ErrorResult
import reprator.willyWeather.base.useCases.WillyWeatherResult

suspend fun <T : Any> safeApiCall(
        call: suspend () -> Flow<WillyWeatherResult<T>>,
        errorMessage: String? = null
): Flow<WillyWeatherResult<T>> {
    return try {
        call()
    } catch (e: Exception) {
        println(e.printStackTrace())
        flowOf(ErrorResult(message = errorMessage ?: e.message))
    }
}