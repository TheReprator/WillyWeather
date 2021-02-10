package reprator.willyWeather.cityList.datasource.remote

import reprator.willyWeather.cityList.datasource.remote.modal.ForecastLocationEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    companion object {
        const val WEATHER_API_KEY = "5ad27815dc7fc065c028d64233764409"
    }

    @GET("forecast/daily")
    suspend fun foreCastWeather(
        @Query("q") lat: String,
        @Query("units") units: String,
        @Query("appid") appid: String = WEATHER_API_KEY,
        @Query("cnt") cnt: Int,
    ): Response<ForecastLocationEntity>
}
