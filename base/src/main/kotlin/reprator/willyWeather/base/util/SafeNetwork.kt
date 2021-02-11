package reprator.willyWeather.base.util
import reprator.willyWeather.base.useCases.ErrorResult
import reprator.willyWeather.base.useCases.WillyWeatherResult

suspend fun <T : Any> safeApiCall(
    call: suspend () -> WillyWeatherResult<T>,
    errorMessage: String? = null
): WillyWeatherResult<T> {
    return try {
        call()
    } catch (e: Exception) {
        println(e.printStackTrace())
        ErrorResult(message = errorMessage ?: e.message)
    }
}