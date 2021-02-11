package reprator.willyWeather.cityList.datasource.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import reprator.willyWeather.base.useCases.ErrorResult
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.base.util.safeApiCall
import reprator.willyWeather.base.util.toResult
import reprator.willyWeather.cityList.data.datasource.ForecastWeatherRemoteDataSource
import reprator.willyWeather.cityList.datasource.remote.remoteMapper.ForecastWeatherMapper
import reprator.willyWeather.cityList.modals.LocationModal
import reprator.willyWeather.cityList.modals.LocationRequestModal
import javax.inject.Inject

class ForeCastWeatherRemoteDataSourceImpl @Inject constructor(
        private val weatherApiService: WeatherApiService,
        private val forecastWeatherMapper: ForecastWeatherMapper
) : ForecastWeatherRemoteDataSource {

    private suspend fun getForecastWeatherApi(requestModal: LocationRequestModal): Flow<WillyWeatherResult<List<LocationModal>>> {
        val data = weatherApiService.foreCastWeather(
                requestModal.location,
                requestModal.unit, cnt = requestModal.count
        ).toResult()

        return when (data) {
            is Success -> {
                flowOf(Success(forecastWeatherMapper.map(data.data)))
            }
            is ErrorResult -> {
                flowOf(ErrorResult(message = data.message, throwable = data.throwable))
            }
            else -> throw IllegalArgumentException()
        }
    }


    override suspend fun getForecastWeather(requestModal: LocationRequestModal): Flow<WillyWeatherResult<List<LocationModal>>> =
            safeApiCall(call = { getForecastWeatherApi(requestModal) })
}