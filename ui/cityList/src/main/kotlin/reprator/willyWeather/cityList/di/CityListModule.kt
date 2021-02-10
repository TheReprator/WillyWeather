package reprator.willyWeather.cityList.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineScope
import reprator.willyWeather.base.util.AppCoroutineDispatchers
import reprator.willyWeather.base.util.ConnectionDetector
import reprator.willyWeather.base.util.DateUtils
import reprator.willyWeather.cityList.data.datasource.ForecastWeatherLocalDataSource
import reprator.willyWeather.cityList.data.datasource.ForecastWeatherRemoteDataSource
import reprator.willyWeather.cityList.data.repository.ForecastWeatherRepositoryImpl
import reprator.willyWeather.cityList.datasource.local.ForeCastWeatherLocalDataSourceImpl
import reprator.willyWeather.cityList.datasource.local.mapper.GetWeatherListMapper
import reprator.willyWeather.cityList.datasource.remote.ForeCastWeatherRemoteDataSourceImpl
import reprator.willyWeather.cityList.datasource.remote.WeatherApiService
import reprator.willyWeather.cityList.datasource.remote.remoteMapper.ForecastWeatherMapper
import reprator.willyWeather.cityList.domain.repository.ForecastWeatherRepository
import reprator.willyWeather.cityList.domain.usecase.ForecastWeatherUseCase
import reprator.willyWeather.database.DBManager
import retrofit2.Retrofit

@InstallIn(ViewModelComponent::class)
@Module
class CityListModule {

    @Provides
    fun provideForecastWeatherMapper(
        dateUtils: DateUtils
    ): ForecastWeatherMapper {
        return ForecastWeatherMapper(dateUtils)
    }

    @Provides
    fun provideForecastWeatherRemoteDataSource(
        weatherApiService: WeatherApiService,
        forecastWeatherMapper: ForecastWeatherMapper
    ): ForecastWeatherRemoteDataSource {
        return ForeCastWeatherRemoteDataSourceImpl(
            weatherApiService,
            forecastWeatherMapper
        )
    }

    @Provides
    fun provideForecastWeatherRepository(
        forecastWeatherRemoteDataSource: ForecastWeatherRemoteDataSource,
        forecastWeatherLocalDataSource: ForecastWeatherLocalDataSource,
        coroutineScope: CoroutineScope,
        coroutineDispatchers: AppCoroutineDispatchers,
        connectionDetector: ConnectionDetector
    ): ForecastWeatherRepository {
        return ForecastWeatherRepositoryImpl(
            forecastWeatherRemoteDataSource,
            forecastWeatherLocalDataSource,
            coroutineScope,
            coroutineDispatchers,
            connectionDetector
        )
    }

    @Provides
    fun provideForecastWeatherLocalDataSource(
        dbManager: DBManager,
        getWeatherListMapper: GetWeatherListMapper
    ): ForecastWeatherLocalDataSource {
        return ForeCastWeatherLocalDataSourceImpl(
            dbManager,
            getWeatherListMapper
        )
    }

    @Provides
    fun provideForecastWeatherUseCase(
        forecastWeatherRepository: ForecastWeatherRepository
    ): ForecastWeatherUseCase {
        return ForecastWeatherUseCase(forecastWeatherRepository)
    }

    @Provides
    fun provideWeatherApiService(
        retrofit: Retrofit
    ): WeatherApiService {
        return retrofit
            .create(WeatherApiService::class.java)
    }
}