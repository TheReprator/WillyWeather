package reprator.willyWeather.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import reprator.willyWeather.implementation.WillyWeatherRoomDb
import reprator.willyWeather.base.util.DateUtils
import reprator.willyWeather.database.DBManager
import reprator.willyWeather.database.DBManagerImpl
import reprator.willyWeather.database.WeatherDao
import timber.log.Timber

@InstallIn(SingletonComponent::class)
@Module
class DBModule {

    companion object {
        private const val DATABASE_NAME = "location_database"
    }

    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context): WillyWeatherRoomDb {
        return Room.databaseBuilder(context, WillyWeatherRoomDb::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Timber.d("RoomDatabaseModule onCreate")
                    }
                })
                .build()
    }

    @Provides
    fun providesCategoryDAO(willyWeatherRoomDb: WillyWeatherRoomDb): WeatherDao =
            willyWeatherRoomDb.weatherDao()

    @Provides
    fun providesDBManager(weatherDao: WeatherDao, dateUtils: DateUtils): DBManager =
            DBManagerImpl(weatherDao, dateUtils)

}