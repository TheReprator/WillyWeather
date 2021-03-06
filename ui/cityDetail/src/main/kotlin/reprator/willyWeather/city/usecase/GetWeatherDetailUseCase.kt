package reprator.willyWeather.city.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import reprator.willyWeather.base.useCases.ErrorResult
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.city.modals.LocationModal
import reprator.willyWeather.database.DBManager
import java.util.*
import javax.inject.Inject

class GetWeatherDetailUseCase @Inject constructor(
    private val dbManager: DBManager,
    private val getWeatherDetailMapper: GetWeatherDetailMapper
) {
    suspend operator fun invoke(id: Long): Flow<WillyWeatherResult<LocationModal>> {
        return when (val data = dbManager.getWeatherItem(Date(id))) {
            is Success -> {
                flowOf(Success(getWeatherDetailMapper.map(data.data)))
            }
            is ErrorResult -> {
                flowOf(ErrorResult(message = data.message, throwable = data.throwable))
            }
            else -> throw IllegalStateException()
        }
    }
}
