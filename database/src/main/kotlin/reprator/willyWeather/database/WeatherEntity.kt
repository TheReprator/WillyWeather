package reprator.willyWeather.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "weatherInfo")
data class WeatherEntity(
        val placeName: String,

        @PrimaryKey
        val weatherDate: Date,
        val timeZone:Long,

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
        val cloudiness: String,
)
