package reprator.willyWeather.cityList.data.datasource

import kotlinx.coroutines.flow.Flow
import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.cityList.modals.LocationModal
import reprator.willyWeather.cityList.modals.LocationRequestModal

interface ForecastWeatherLocalDataSource {
    suspend fun getLocationList(): Flow<WillyWeatherResult<List<LocationModal>>>
    suspend fun clearAllRecords(): Flow<WillyWeatherResult<Int>>
    suspend fun insertAllRecords(locationalList: List<LocationModal>): Flow<WillyWeatherResult<List<Long>>>
}