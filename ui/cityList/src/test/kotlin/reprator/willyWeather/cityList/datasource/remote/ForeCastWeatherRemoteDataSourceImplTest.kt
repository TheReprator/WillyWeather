package reprator.willyWeather.cityList.datasource.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.cityList.TestFakeData.getFakeLocationModalDataList
import reprator.willyWeather.cityList.TestFakeData.getFakeRemoteDataList
import reprator.willyWeather.cityList.data.datasource.ForecastWeatherRemoteDataSource
import reprator.willyWeather.cityList.datasource.remote.remoteMapper.ForecastWeatherMapper
import reprator.willyWeather.cityList.modals.LocationRequestModal
import reprator.willyWeather.testUtils.MainCoroutineRule
import retrofit2.Response
import java.net.UnknownHostException

@RunWith(JUnit4::class)
class ForeCastWeatherRemoteDataSourceImplTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = MainCoroutineRule()

    @MockK
    lateinit var weatherApiService: WeatherApiService

    @MockK
    lateinit var forecastWeatherMapper: ForecastWeatherMapper

    private lateinit var foreCastWeatherRemoteDataSource: ForecastWeatherRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, true)

        foreCastWeatherRemoteDataSource = ForeCastWeatherRemoteDataSourceImpl(
            weatherApiService,
            forecastWeatherMapper
        )
    }

    @Test
    fun `fetch list successful from server`() = coroutinesTestRule.runBlockingTest {

        val input = LocationRequestModal("London")

        coEvery {
            weatherApiService.foreCastWeather(any(), any(), any(), any())
        } returns
                Response.success(getFakeRemoteDataList())

        coEvery {
            forecastWeatherMapper.map(any())
        } returns getFakeLocationModalDataList()

        val result = foreCastWeatherRemoteDataSource.getForecastWeather(input).single()

        Truth.assertThat(result).isInstanceOf(Success::class.java)
        Truth.assertThat(result.get()!!).hasSize(4)
        Truth.assertThat(result.get()!![0]).isEqualTo(getFakeLocationModalDataList()[0])
    }

    @Test
    fun `fetch list failed from server`() = coroutinesTestRule.runBlockingTest {

        val input = LocationRequestModal("London")
        val output = "Invalid data received"
        coEvery {
            weatherApiService.foreCastWeather(any(), any(), any(), any())
        } throws UnknownHostException(output)

        foreCastWeatherRemoteDataSource.getForecastWeather(input)
            .catch { error ->
                Truth.assertThat(error.message).isEqualTo(output)
                Truth.assertThat(error).isInstanceOf(UnknownHostException::class.java)
            }.collect()
    }
}