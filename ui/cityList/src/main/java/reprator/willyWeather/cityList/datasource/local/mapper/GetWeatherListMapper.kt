package reprator.willyWeather.cityList.datasource.local.mapper

import reprator.willyWeather.base.util.MapperConversion
import reprator.willyWeather.cityList.modals.LocationModal
import reprator.willyWeather.database.LocationEntity
import javax.inject.Inject

class GetWeatherListMapper @Inject constructor() :
        MapperConversion<LocationEntity, LocationModal> {

    override suspend fun mapTo(from: LocationEntity): LocationModal {
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
                from.sunset,
                from.locationId
        )
    }

    override suspend fun mapIn(from: LocationModal): LocationEntity {
        return LocationEntity(
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
