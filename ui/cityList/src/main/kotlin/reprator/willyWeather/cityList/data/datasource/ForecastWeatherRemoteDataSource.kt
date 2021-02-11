package reprator.willyWeather.cityList.data.datasource

import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.cityList.modals.LocationModal
import reprator.willyWeather.cityList.modals.LocationRequestModal

interface ForecastWeatherRemoteDataSource {
    suspend fun getForecastWeather(requestModal: LocationRequestModal): WillyWeatherResult<List<LocationModal>>
}