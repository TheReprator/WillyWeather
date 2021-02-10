package reprator.willyWeather.city.usecase

import reprator.willyWeather.base.util.Mapper
import reprator.willyWeather.city.modals.LocationModal
import reprator.willyWeather.database.WeatherEntity
import javax.inject.Inject

class GetWeatherDetailMapper @Inject constructor() :
    Mapper<WeatherEntity, LocationModal> {

    override suspend fun map(from: WeatherEntity): LocationModal {
        return LocationModal(
            from.placeName,

            from.weatherDate,
            from.timeZone,

            from.sunset,
            from.sunrise,

            from.minTemperature,
            from.maxTemperature,

            from.humidity,
            from.pressure,

            from.weather,

            from.windSpeed,
            from.windDegree,

            from.snowVolume,
            from.cloudiness
        )
    }
}
