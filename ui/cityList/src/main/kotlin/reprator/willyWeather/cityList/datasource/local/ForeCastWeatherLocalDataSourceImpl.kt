package reprator.willyWeather.cityList.datasource.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import reprator.willyWeather.base.useCases.ErrorResult
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.base.util.safeApiCall
import reprator.willyWeather.cityList.data.datasource.ForecastWeatherLocalDataSource
import reprator.willyWeather.cityList.datasource.local.mapper.GetWeatherListMapper
import reprator.willyWeather.cityList.modals.LocationModal
import reprator.willyWeather.database.DBManager
import timber.log.Timber
import javax.inject.Inject

class ForeCastWeatherLocalDataSourceImpl @Inject constructor(
    private val dbManager: DBManager,
    private val getWeatherListMapper: GetWeatherListMapper
) : ForecastWeatherLocalDataSource {

    private suspend fun getLocationListDb():
            Flow<WillyWeatherResult<List<LocationModal>>> {

        return when (val data = dbManager.getWeatherList().single()) {
            is Success -> {
                val mappedData = data.get().map {
                    getWeatherListMapper.mapTo(it)
                }
                flowOf(Success(mappedData))
            }
            is ErrorResult -> {
                flowOf(ErrorResult(message = "An Error Occurred"))
            }
        }
    }

    override suspend fun getLocationList(): Flow<WillyWeatherResult<List<LocationModal>>> =
        safeApiCall(call = { getLocationListDb() })

    override suspend fun clearAllRecords(): Flow<WillyWeatherResult<Int>> {
        return dbManager.clearTable()
    }

    override suspend fun insertAllRecords(locationalList: List<LocationModal>): Flow<WillyWeatherResult<List<Long>>> {
        val modifiedList = locationalList.map {
            getWeatherListMapper.mapIn(it)
        }
        Timber.e("savedate ${modifiedList}")
        return dbManager.saveWeatherList(modifiedList)
    }
}