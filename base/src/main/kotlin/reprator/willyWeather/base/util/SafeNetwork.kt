package reprator.willyWeather.base.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.base.useCases.ErrorResult

suspend fun <T : Any> safeApiCall(
    call: suspend () -> Flow<WillyWeatherResult<T>>,
    errorMessage: String? = null
): Flow<WillyWeatherResult<T>> {
    return try {
        call()
    } catch (e: Exception) {
        println(e.printStackTrace())
        flow {
            emit(ErrorResult(message = errorMessage ?: e.message))
        }
    }
}