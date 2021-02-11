package reprator.willyWeather.cityList.datasource.local

import reprator.willyWeather.base.useCases.ErrorResult
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.base.util.safeApiCall
import reprator.willyWeather.cityList.data.datasource.ForecastWeatherLocalDataSource
import reprator.willyWeather.cityList.datasource.local.mapper.GetWeatherListMapper
import reprator.willyWeather.cityList.modals.LocationModal
import reprator.willyWeather.database.DBManager
import javax.inject.Inject

class ForeCastWeatherLocalDataSourceImpl @Inject constructor(
    private val dbManager: DBManager,
    private val getWeatherListMapper: GetWeatherListMapper
) : ForecastWeatherLocalDataSource {

    private suspend fun getLocationListDb(): WillyWeatherResult<List<LocationModal>> {
        return when (val data = dbManager.getWeatherList()) {
            is Success -> {
                val mappedData = data.get().map {
                    getWeatherListMapper.mapTo(it)
                }
                Success(mappedData)
            }
            is ErrorResult -> {
                ErrorResult(message = "An Error Occurred")
            }
            else -> throw IllegalStateException()
        }
    }

    override suspend fun getLocationList(): WillyWeatherResult<List<LocationModal>> =
        safeApiCall(call = { getLocationListDb() })

    override suspend fun clearAllRecords(): WillyWeatherResult<Int> {
        return dbManager.clearTable()
    }

    override suspend fun insertAllRecords(locationalList: List<LocationModal>): WillyWeatherResult<List<Long>> {
        val modifiedList = locationalList.map {
            getWeatherListMapper.mapIn(it)
        }
        return dbManager.saveWeatherList(modifiedList)
    }
}