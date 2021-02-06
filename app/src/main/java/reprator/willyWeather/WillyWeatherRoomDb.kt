package reprator.willyWeather

import androidx.room.Database
import androidx.room.RoomDatabase
import reprator.willyWeather.database.LocationDao
import reprator.willyWeather.database.LocationEntity

@Database(
    entities = [LocationEntity::class],
    version = 1, exportSchema = true
)
abstract class WillyWeatherRoomDb : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}

