package reprator.willyWeather.database

import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.base.util.DateUtils
import reprator.willyWeather.base.util.safeApiCall
import java.util.*
import javax.inject.Inject

interface DBManager {
    suspend fun saveWeather(weatherEntity: WeatherEntity): WillyWeatherResult<Long>
    suspend fun saveWeatherList(weatherEntity: List<WeatherEntity>): WillyWeatherResult<List<Long>>
    suspend fun getWeatherList(): WillyWeatherResult<List<WeatherEntity>>
    suspend fun getWeatherItem(date: Date): WillyWeatherResult<WeatherEntity>
    suspend fun deleteWeather(weatherEntity: WeatherEntity): WillyWeatherResult<Int>
    suspend fun clearTable(): WillyWeatherResult<Int>
}

class DBManagerImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val dateUtils: DateUtils
) : DBManager {

    private suspend fun saveWeatherDB(weatherEntity: WeatherEntity): WillyWeatherResult<Long> {
        val data = weatherDao.insertWeather(weatherEntity)
        return Success(data)
    }

    override suspend fun saveWeather(weatherEntity: WeatherEntity): WillyWeatherResult<Long> =
        safeApiCall(call = { saveWeatherDB(weatherEntity) })

    private suspend fun saveWeatherListDB(weatherEntity: List<WeatherEntity>): WillyWeatherResult<List<Long>> {
        val data = weatherDao.insertWeatherList(*weatherEntity.toTypedArray())
        return Success(data)
    }

    override suspend fun saveWeatherList(weatherEntity: List<WeatherEntity>):
            WillyWeatherResult<List<Long>> =
        safeApiCall(call = { saveWeatherListDB(weatherEntity) })

    private fun getWeatherListDB(date: Date): WillyWeatherResult<List<WeatherEntity>> {
        val data = weatherDao.getWeatherList(date)
        return if (data.isNullOrEmpty())
            Success(emptyList<WeatherEntity>())
        else
            Success(data)
    }

    override suspend fun getWeatherList(): WillyWeatherResult<List<WeatherEntity>> =
        safeApiCall(call = { getWeatherListDB(dateUtils.getCalendar().time) })

    private fun getWeatherItemDB(date: Date): WillyWeatherResult<WeatherEntity> {
        return Success(weatherDao.getWeatherItem(date))
    }

    override suspend fun getWeatherItem(date: Date): WillyWeatherResult<WeatherEntity> =
        safeApiCall(call = { getWeatherItemDB(date) })

    private suspend fun deleteWeatherDB(weatherEntity: WeatherEntity): WillyWeatherResult<Int> {
        val longList = weatherDao.deleteWeather(weatherEntity)
        return Success(longList)
    }

    override suspend fun deleteWeather(weatherEntity: WeatherEntity): WillyWeatherResult<Int> =
        safeApiCall(call = { deleteWeatherDB(weatherEntity) })

    private suspend fun clearTableDB(): WillyWeatherResult<Int> {
        val longList = weatherDao.clearTable()
        return Success(longList)
    }

    override suspend fun clearTable(): WillyWeatherResult<Int> =
        safeApiCall(call = { clearTableDB() })
}