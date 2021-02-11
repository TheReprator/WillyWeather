package reprator.willyWeather.cityList.data.datasource

import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.cityList.modals.LocationModal

interface ForecastWeatherLocalDataSource {
    suspend fun getLocationList(): WillyWeatherResult<List<LocationModal>>
    suspend fun clearAllRecords(): WillyWeatherResult<Int>
    suspend fun insertAllRecords(locationalList: List<LocationModal>): WillyWeatherResult<List<Long>>
}