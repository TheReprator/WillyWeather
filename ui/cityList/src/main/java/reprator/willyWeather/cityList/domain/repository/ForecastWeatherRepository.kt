package reprator.willyWeather.cityList.domain.repository

import kotlinx.coroutines.flow.Flow
import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.cityList.modals.LocationModal
import reprator.willyWeather.cityList.modals.LocationRequestModal

interface ForecastWeatherRepository {
    suspend fun getForeCastWeatherRepository(requestModal: LocationRequestModal):
            Flow<WillyWeatherResult<List<LocationModal>>>
}