package reprator.willyWeather.cityList.datasource.remote

import reprator.willyWeather.cityList.datasource.remote.modal.ForecastLocationEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    companion object {
        const val CURRENCY_API_KEY = "fae7190d7e6433ec3a45285ffcf55c86"
    }

    @GET("forecast")
    suspend fun foreCastWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appid: String = CURRENCY_API_KEY,
        @Query("cnt") cnt: Int,
    ): Response<ForecastLocationEntity>
}

//http://api.openweathermap.org/data/2.5/forecast?lat=30.70&lon=76.71&cnt=2&appid=fae7190d7e6433ec3a45285ffcf55c86&units=metric