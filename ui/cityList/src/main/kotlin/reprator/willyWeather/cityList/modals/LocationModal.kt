package reprator.willyWeather.cityList.modals

data class LocationModal(
    val weather: String,

    val temperature: String,
    val minTemperature: String,
    val maxTemperature: String,

    val pressure: String,
    val humidity: String,

    val windSpeed: String,
    val windDegree: String,

    val sunset: String,
    val sunrise: String,

    val id: Int = 0
)