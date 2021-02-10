package reprator.willyWeather.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.base.util.DateUtils
import reprator.willyWeather.base.util.safeApiCall
import java.util.*
import javax.inject.Inject

interface DBManager {
    suspend fun saveWeather(weatherEntity: WeatherEntity): Flow<WillyWeatherResult<Long>>
    suspend fun saveWeatherList(weatherEntity: List<WeatherEntity>): Flow<WillyWeatherResult<List<Long>>>
    suspend fun getWeatherList(): Flow<WillyWeatherResult<List<WeatherEntity>>>
    suspend fun getWeatherItem(date: Date): Flow<WillyWeatherResult<WeatherEntity>>
    suspend fun deleteWeather(weatherEntity: WeatherEntity): Flow<WillyWeatherResult<Int>>
    suspend fun clearTable(): Flow<WillyWeatherResult<Int>>
}

class DBManagerImpl @Inject constructor(private val weatherDao: WeatherDao,
                                        private val dateUtils: DateUtils) : DBManager {

    private suspend fun saveWeatherDB(weatherEntity: WeatherEntity): Flow<WillyWeatherResult<Long>> {
        val data = weatherDao.insertWeather(weatherEntity)
        return flowOf(Success(data))
    }

    override suspend fun saveWeather(weatherEntity: WeatherEntity): Flow<WillyWeatherResult<Long>> =
            safeApiCall(call = { saveWeatherDB(weatherEntity) })

    private suspend fun saveWeatherListDB(weatherEntity: List<WeatherEntity>): Flow<WillyWeatherResult<List<Long>>> {
        val data = weatherDao.insertWeatherList(*weatherEntity.toTypedArray())
        return flowOf(Success(data))
    }

    override suspend fun saveWeatherList(weatherEntity: List<WeatherEntity>):
            Flow<WillyWeatherResult<List<Long>>> =
            safeApiCall(call = { saveWeatherListDB(weatherEntity) })

    private suspend fun getWeatherListDB(date: Date): Flow<WillyWeatherResult<List<WeatherEntity>>> {
        return flow {
            val data = weatherDao.getWeatherList(date)
            if (data.isNullOrEmpty())
                emit(Success(emptyList<WeatherEntity>()))
            else
                emit(Success(data))
        }
    }

    override suspend fun getWeatherList(): Flow<WillyWeatherResult<List<WeatherEntity>>> =
            safeApiCall(call = { getWeatherListDB(dateUtils.getCalendar().time) })

    private fun getWeatherItemDB(date: Date): Flow<WillyWeatherResult<WeatherEntity>> {
        return flowOf(Success(weatherDao.getWeatherItem(date)))
    }

    override suspend fun getWeatherItem(date: Date): Flow<WillyWeatherResult<WeatherEntity>> =
            safeApiCall(call = { getWeatherItemDB(date) })

    private suspend fun deleteWeatherDB(weatherEntity: WeatherEntity): Flow<WillyWeatherResult<Int>> {
        val longList = weatherDao.deleteWeather(weatherEntity)
        return flowOf(Success(longList))
    }

    override suspend fun deleteWeather(weatherEntity: WeatherEntity): Flow<WillyWeatherResult<Int>> =
            safeApiCall(call = { deleteWeatherDB(weatherEntity) })

    private suspend fun clearTableDB(): Flow<WillyWeatherResult<Int>> {
        val longList = weatherDao.clearTable()
        return flowOf(Success(longList))
    }

    override suspend fun clearTable(): Flow<WillyWeatherResult<Int>> =
            safeApiCall(call = { clearTableDB() })
}