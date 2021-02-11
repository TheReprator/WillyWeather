package reprator.willyWeather.cityList.datasource.remote.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.base.util.bodyOrThrow
import reprator.willyWeather.cityList.datasource.remote.WeatherApiService
import reprator.willyWeather.cityList.datasource.remote.WeatherApiService.Companion.WEATHER_API_KEY
import reprator.willyWeather.cityList.datasource.remote.api.setup.createJacksonConverterFactory
import reprator.willyWeather.cityList.datasource.remote.api.setup.enqueueResponse
import retrofit2.Retrofit
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit


@RunWith(JUnit4::class)
class ForeCastWeatherApiTest {

    companion object {
        private const val LOCATION_NAME = "london"
        private const val UNIT = "standard"
        private const val COUNT = 7
    }

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: WeatherApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS) // For testing purposes
            .readTimeout(2, TimeUnit.SECONDS) // For testing purposes
            .writeTimeout(2, TimeUnit.SECONDS)
            .build()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(createJacksonConverterFactory())
            .build()
            .create(WeatherApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get weather forecast for next 7 days`() = runBlocking {
        javaClass.enqueueResponse(mockWebServer, "forecast.json")
        val foreCastEntity = service.foreCastWeather(LOCATION_NAME, UNIT, cnt = COUNT).body()
        val request = mockWebServer.takeRequest()

        Truth.assertThat(foreCastEntity).isNotNull()

        val foreCastList = foreCastEntity!!.list
        Truth.assertThat(foreCastList.size).isEqualTo(COUNT)

        Truth.assertThat(request.requestUrl!!.pathSegments.size).isEqualTo(2)
        Truth.assertThat(request.requestUrl!!.pathSegments[0]).isEqualTo("forecast")
        Truth.assertThat(request.requestUrl!!.pathSegments[1]).isEqualTo("daily")

        val queryParams = request.requestUrl!!.queryParameterNames
        Truth.assertThat(queryParams).hasSize(4)
        Truth.assertThat(queryParams).contains("q")

        val requestParams = setOf("q", "units", "appid", "cnt")
        Truth.assertThat(queryParams).isEqualTo(requestParams)

        Truth.assertThat(request.path)
            .isEqualTo("/forecast/daily?q=$LOCATION_NAME&units=$UNIT&appid=$WEATHER_API_KEY&cnt=$COUNT")
        Truth.assertThat(request.method).isEqualTo("GET")
    }

    @Test(expected = SocketTimeoutException::class)
    fun `Timeout example`() = runBlocking {
        val response = MockResponse()
            .setSocketPolicy(SocketPolicy.NO_RESPONSE)
            .throttleBody(1, 2, TimeUnit.SECONDS)

        mockWebServer.enqueue(response)

        val execute = service.foreCastWeather(LOCATION_NAME, UNIT, cnt = COUNT).bodyOrThrow()
    }
}