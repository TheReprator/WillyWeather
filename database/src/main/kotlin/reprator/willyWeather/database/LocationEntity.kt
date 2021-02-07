package reprator.willyWeather.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(
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

    @PrimaryKey(autoGenerate = true)
    val locationId: Int = 0,
)
