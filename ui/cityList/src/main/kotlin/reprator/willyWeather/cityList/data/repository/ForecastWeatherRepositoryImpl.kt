package reprator.willyWeather.cityList.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import reprator.willyWeather.base.useCases.ErrorResult
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.base.util.AppCoroutineDispatchers
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
        private val coroutineScope: CoroutineScope,
        private val coroutineDispatchers: AppCoroutineDispatchers,
        private val connectionDetector: ConnectionDetector,
) : ForecastWeatherRepository {

    override suspend fun getForeCastWeatherRepository(requestModal: LocationRequestModal):
            Flow<WillyWeatherResult<List<LocationModal>>> {
        return if (connectionDetector.isInternetAvailable)
            saveAndFetchFromDb(requestModal)
        else
            getDataFromDb()
    }

    private suspend fun saveAndFetchFromDb(requestModal: LocationRequestModal) =
            suspendCancellableCoroutine<Flow<WillyWeatherResult<List<LocationModal>>>> { cont ->
                coroutineScope.launch(coroutineDispatchers.io) {
                    forecastWeatherRemoteDataSource.getForecastWeather(requestModal)
                            .also { flowResult ->
                                flowResult.catch { error ->
                                    // error.printStackTrace()
                                    // cont.resumeWithException(error)
                                    cont.resume(getDataFromDb()) { error }
                                }.collect {
                                    if (it is Success && it.data.isNotEmpty()) {
                                        forecastWeatherLocalDataSource.clearAllRecords()
                                        forecastWeatherLocalDataSource.insertAllRecords(it.data)

                                        cont.resume(getDataFromDb()) {}
                                    }
                                }
                            }
                }
            }

    private suspend fun getDataFromDb(): Flow<WillyWeatherResult<List<LocationModal>>> {
        return when (val data = forecastWeatherLocalDataSource.getLocationList().single()) {
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