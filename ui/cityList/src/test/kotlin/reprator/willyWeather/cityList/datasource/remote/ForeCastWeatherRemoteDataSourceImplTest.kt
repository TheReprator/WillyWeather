package reprator.willyWeather.cityList.datasource.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.base.useCases.ErrorResult
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.cityList.TestFakeData.getFakeLocationModalDataList
import reprator.willyWeather.cityList.TestFakeData.getFakeRemoteDataList
import reprator.willyWeather.cityList.data.datasource.ForecastWeatherRemoteDataSource
import reprator.willyWeather.cityList.datasource.remote.remoteMapper.ForecastWeatherMapper
import reprator.willyWeather.cityList.modals.LocationRequestModal
import reprator.willyWeather.testUtils.MainCoroutineRule
import retrofit2.HttpException
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
        } returns Response.success(getFakeRemoteDataList())

        coEvery {
            forecastWeatherMapper.map(any())
        } returns getFakeLocationModalDataList()

        val result = foreCastWeatherRemoteDataSource.getForecastWeather(input)

        Truth.assertThat(result).isInstanceOf(Success::class.java)
        Truth.assertThat(result.get()!!).hasSize(4)
        Truth.assertThat(result.get()!![0]).isEqualTo(getFakeLocationModalDataList()[0])

        coVerifySequence {
            weatherApiService.foreCastWeather(any(), any(), any(), any())
            forecastWeatherMapper.map(any())
        }
    }

    @Test
    fun `fetch list failed from server`() = coroutinesTestRule.runBlockingTest {

        val input = LocationRequestModal("London")
        val output = "No Address Associated"

        coEvery {
            weatherApiService.foreCastWeather(any(), any(), any(), any())
        }.throws(UnknownHostException(output))

        val result = foreCastWeatherRemoteDataSource.getForecastWeather(input)

        Truth.assertThat(result).isInstanceOf(ErrorResult::class.java)
        Truth.assertThat((result as ErrorResult).message).isEqualTo(output)
    }

    @Test
    fun `fetch list failed with errorBody`() = coroutinesTestRule.runBlockingTest {

        val input = LocationRequestModal("London")

        coEvery {
            weatherApiService.foreCastWeather(any(), any(), any(), any())
        } returns Response.error(404, mockk(relaxed = true))

        val resp = foreCastWeatherRemoteDataSource.getForecastWeather(input)

        Truth.assertThat(resp).isInstanceOf(ErrorResult::class.java)
        Truth.assertThat((resp as ErrorResult).throwable).isInstanceOf(HttpException::class.java)
    }
}