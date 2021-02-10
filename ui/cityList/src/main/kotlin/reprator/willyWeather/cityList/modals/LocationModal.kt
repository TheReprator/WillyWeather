package reprator.willyWeather.cityList.modals

import java.util.*

data class LocationModal(
    val placeName: String,
    val weatherDate: Date,
    val timeZone: Long,

    val sunset: String,
    val sunrise: String,

    val minTemperature: String,
    val maxTemperature: String,

    val pressure: String,
    val humidity: String,

    val weather: String,

    val windSpeed: String,
    val windDegree: String,

    val snowVolume: String,
    val cloudiness: String
)