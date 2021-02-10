package reprator.willyWeather.database

import androidx.room.*
import java.util.*

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherEntity: WeatherEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherList(vararg weatherEntity: WeatherEntity): List<Long>

    @Query("SELECT * FROM weatherInfo where weatherDate > :todayDate")
    fun getWeatherList(todayDate: Date): List<WeatherEntity>

    @Query("SELECT * FROM weatherInfo")
    fun getWeatherList(): List<WeatherEntity>

    @Query("SELECT * FROM weatherInfo where weatherDate == :date ")
    fun getWeatherItem(date: Date): WeatherEntity

    @Delete
    suspend fun deleteWeather(weatherEntity: WeatherEntity): Int

    @Query("DELETE FROM weatherInfo")
    suspend fun clearTable(): Int
}
