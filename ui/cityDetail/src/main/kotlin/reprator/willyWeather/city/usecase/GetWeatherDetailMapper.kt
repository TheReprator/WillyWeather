package reprator.willyWeather.city.usecase

import reprator.willyWeather.base.util.Mapper
import reprator.willyWeather.city.modals.LocationModal
import reprator.willyWeather.database.LocationEntity
import javax.inject.Inject

class GetWeatherDetailMapper @Inject constructor() :
    Mapper<LocationEntity, LocationModal> {

    override suspend fun map(from: LocationEntity): LocationModal {
        return LocationModal(
            from.weather,
            from.temperature,
            from.minTemperature,
            from.maxTemperature,

            from.pressure,
            from.humidity,

            from.windSpeed,
            from.windDegree,

            from.sunrise,
            from.sunset
        )
    }
}
