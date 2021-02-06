package reprator.willyWeather.cityList.domain.usecase

import kotlinx.coroutines.flow.Flow
import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.cityList.domain.repository.ForecastWeatherRepository
import reprator.willyWeather.cityList.modals.LocationModal
import reprator.willyWeather.cityList.modals.LocationRequestModal
import javax.inject.Inject

class ForecastWeatherUseCase @Inject constructor(
    private val forecastWeatherRepository: ForecastWeatherRepository
) {
    suspend operator fun invoke(requestModal: LocationRequestModal): Flow<WillyWeatherResult<List<LocationModal>>> {
        return forecastWeatherRepository.getForeCastWeatherRepository(requestModal)
    }
}
