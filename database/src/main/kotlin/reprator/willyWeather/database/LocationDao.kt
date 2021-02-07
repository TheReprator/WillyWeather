package reprator.willyWeather.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationEntity: LocationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationList(vararg locationEntity: LocationEntity): List<Long>

    @Query("SELECT * FROM locations")
    fun getLocationList(): List<LocationEntity>

    @Query("SELECT * FROM locations where locationId =:id ")
    fun getLocationDetail(id: Int): LocationEntity

    @Delete
    suspend fun deleteLocation(locationEntity: LocationEntity): Int

    @Query("DELETE FROM locations")
    suspend fun clearTable(): Int
}
