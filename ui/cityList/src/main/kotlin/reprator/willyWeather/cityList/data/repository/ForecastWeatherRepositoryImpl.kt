package reprator.willyWeather.cityList.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import reprator.willyWeather.base.useCases.ErrorResult
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.base.util.ConnectionDetector
import reprator.willyWeather.cityList.data.datasource.ForecastWeatherLocalDataSource
import reprator.willyWeather.cityList.data.datasource.ForecastWeatherRemoteDataSource
import reprator.willyWeather.cityList.domain.repository.ForecastWeatherRepository
import reprator.willyWeather.cityList.modals.LocationModal
import reprator.willyWeather.cityList.modals.LocationRequestModal
import javax.inject.Inject

@ViewModelScoped
class ForecastWeatherRepositoryImpl @Inject constructor(
    private val forecastWeatherRemoteDataSource: ForecastWeatherRemoteDataSource,
    private val forecastWeatherLocalDataSource: ForecastWeatherLocalDataSource,
    private val connectionDetector: ConnectionDetector,
) : ForecastWeatherRepository {

    override suspend fun getForeCastWeatherRepository(requestModal: LocationRequestModal):
            Flow<WillyWeatherResult<List<LocationModal>>> {
        return if (connectionDetector.isInternetAvailable)
            saveAndFetchFromDb(requestModal)
        else
            getDataFromDb()
    }

    private suspend fun saveAndFetchFromDb(requestModal: LocationRequestModal):
            Flow<WillyWeatherResult<List<LocationModal>>> {

        return when (val result =
            forecastWeatherRemoteDataSource.getForecastWeather(requestModal)) {
            is Success -> {
                forecastWeatherLocalDataSource.clearAllRecords()
                forecastWeatherLocalDataSource.insertAllRecords(result.data)

                getDataFromDb()
            }
            is ErrorResult -> {
                getDataFromDb()
            }
            else -> throw IllegalStateException()
        }
    }

    private suspend fun getDataFromDb(): Flow<WillyWeatherResult<List<LocationModal>>> {
        return when (val data = forecastWeatherLocalDataSource.getLocationList()) {
            is Success -> {
                if (data.data.isNullOrEmpty())
                    flowOf(ErrorResult(message = "No internet connection."))
                else
                    flowOf(Success(data.data))
            }
            is ErrorResult -> {
                flowOf(ErrorResult(message = data.message, throwable = data.throwable))
            }
            else -> throw IllegalStateException()
        }
    }
}