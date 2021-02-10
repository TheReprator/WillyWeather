package reprator.willyWeather.cityList.datasource.local.mapper

import reprator.willyWeather.base.util.MapperConversion
import reprator.willyWeather.cityList.modals.LocationModal
import reprator.willyWeather.database.WeatherEntity
import javax.inject.Inject

class GetWeatherListMapper @Inject constructor() :
    MapperConversion<WeatherEntity, LocationModal> {

    override suspend fun mapTo(from: WeatherEntity): LocationModal {
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

    override suspend fun mapIn(from: LocationModal): WeatherEntity {
        return WeatherEntity(
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
