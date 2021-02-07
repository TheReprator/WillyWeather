package reprator.willyWeather.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.base.useCases.WillyWeatherResult
import reprator.willyWeather.base.util.safeApiCall
import javax.inject.Inject

interface DBManager {

    suspend fun saveLocation(locationEntity: LocationEntity): Flow<WillyWeatherResult<Long>>
    suspend fun saveLocationList(locationEntity: List<LocationEntity>): Flow<WillyWeatherResult<List<Long>>>
    suspend fun getLocationList(): Flow<WillyWeatherResult<List<LocationEntity>>>
    suspend fun getLocation(id: Int): Flow<WillyWeatherResult<LocationEntity>>
    suspend fun deleteLocation(locationEntity: LocationEntity): Flow<WillyWeatherResult<Int>>
    suspend fun clearTable(): Flow<WillyWeatherResult<Int>>
}

class DBManagerImpl @Inject constructor(private val locationDao: LocationDao) : DBManager {

    private suspend fun saveLocationDB(locationEntity: LocationEntity): Flow<WillyWeatherResult<Long>> {
        val data = locationDao.insertLocation(locationEntity)
        return flowOf(Success(data))
    }

    override suspend fun saveLocation(locationEntity: LocationEntity): Flow<WillyWeatherResult<Long>> =
        safeApiCall(call = { saveLocationDB(locationEntity) })

    private suspend fun saveLocationListDB(locationEntity: List<LocationEntity>): Flow<WillyWeatherResult<List<Long>>> {
        val data = locationDao.insertLocationList(*locationEntity.toTypedArray())
        return flowOf(Success(data))
    }

    override suspend fun saveLocationList(locationEntity: List<LocationEntity>): Flow<WillyWeatherResult<List<Long>>> =
        safeApiCall(call = { saveLocationListDB(locationEntity) })

    private suspend fun getLocationListDB(): Flow<WillyWeatherResult<List<LocationEntity>>> {
        return flow {
            val data = locationDao.getLocationList()
            if (data.isNullOrEmpty())
                emit(Success(emptyList<LocationEntity>()))
            else
                emit(Success(data))
        }
    }

    override suspend fun getLocationList(): Flow<WillyWeatherResult<List<LocationEntity>>> =
        safeApiCall(call = { getLocationListDB() })

    private suspend fun getLocationDB(id: Int): Flow<WillyWeatherResult<LocationEntity>> {
        return flowOf(Success(locationDao.getLocationDetail(id)))
    }

    override suspend fun getLocation(id: Int): Flow<WillyWeatherResult<LocationEntity>> =
        safeApiCall(call = { getLocationDB(id) })

    private suspend fun deleteLocationDB(locationEntity: LocationEntity): Flow<WillyWeatherResult<Int>> {
        val longList = locationDao.deleteLocation(locationEntity)
        return flowOf(Success(longList))
    }

    override suspend fun deleteLocation(locationEntity: LocationEntity): Flow<WillyWeatherResult<Int>> =
        safeApiCall(call = { deleteLocationDB(locationEntity) })

    private suspend fun deleteAllLocationDB(): Flow<WillyWeatherResult<Int>> {
        val longList = locationDao.clearTable()
        return flowOf(Success(longList))
    }

    override suspend fun clearTable(): Flow<WillyWeatherResult<Int>> =
        safeApiCall(call = { deleteAllLocationDB() })
}