package reprator.willyWeather.implementation

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import reprator.willyWeather.database.DateConverter
import reprator.willyWeather.database.WeatherDao
import reprator.willyWeather.database.WeatherEntity

@Database(
        entities = [WeatherEntity::class],
        version = 1, exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class WillyWeatherRoomDb : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}

