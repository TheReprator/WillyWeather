package reprator.willyWeather.cityList.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.base.useCases.ErrorResult
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.base.util.ConnectionDetector
import reprator.willyWeather.cityList.TestFakeData.getFakeLocationModalDataList
import reprator.willyWeather.cityList.data.datasource.ForecastWeatherLocalDataSource
import reprator.willyWeather.cityList.data.datasource.ForecastWeatherRemoteDataSource
import reprator.willyWeather.cityList.domain.repository.ForecastWeatherRepository
import reprator.willyWeather.cityList.modals.LocationRequestModal
import reprator.willyWeather.testUtils.MainCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ForecastWeatherRepositoryImplTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = MainCoroutineRule()

    @MockK
    lateinit var forecastWeatherRemoteDataSource: ForecastWeatherRemoteDataSource

    @MockK
    lateinit var forecastWeatherLocalDataSource: ForecastWeatherLocalDataSource

    @MockK
    lateinit var connectionDetector: ConnectionDetector

    lateinit var forecastWeatherRepository: ForecastWeatherRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)

        forecastWeatherRepository = ForecastWeatherRepositoryImpl(
            forecastWeatherRemoteDataSource,
            forecastWeatherLocalDataSource, connectionDetector
        )
    }

    @Test
    fun `get weather data from internet on available, then clear, save & fetch from DB`() =
        coroutinesTestRule.runBlockingTest {

            val input = LocationRequestModal("London")
            val output = getFakeLocationModalDataList()

            coEvery {
                connectionDetector.isInternetAvailable
            } returns true

            coEvery {
                forecastWeatherRemoteDataSource.getForecastWeather(any())
            } returns Success(output)

            coEvery {
                forecastWeatherLocalDataSource.clearAllRecords()
            } returns mockk(relaxed = true)

            coEvery {
                forecastWeatherLocalDataSource.insertAllRecords(any())
            } returns mockk(relaxed = true)

            coEvery {
                forecastWeatherLocalDataSource.getLocationList()
            } returns Success(output)

            val result = forecastWeatherRepository.getForeCastWeatherRepository(input).single()

            Truth.assertThat(result).isInstanceOf(Success::class.java)
            Truth.assertThat(result.get()!!).hasSize(4)

            coVerifySequence {
                connectionDetector.isInternetAvailable
                forecastWeatherRemoteDataSource.getForecastWeather(any())
                forecastWeatherLocalDataSource.clearAllRecords()
                forecastWeatherLocalDataSource.insertAllRecords(any())
                forecastWeatherLocalDataSource.getLocationList()
            }

            coVerify(atMost =1 ) {
                connectionDetector.isInternetAvailable
                forecastWeatherRemoteDataSource.getForecastWeather(any())
                forecastWeatherLocalDataSource.clearAllRecords()
                forecastWeatherLocalDataSource.insertAllRecords(any())
                forecastWeatherLocalDataSource.getLocationList()
            }
        }

    @Test
    fun `get weather data from localdb, if internet is available but failed to download data`() =
        coroutinesTestRule.runBlockingTest {

            val input = LocationRequestModal("London")
            val errorMsg = "Failed to hit server"

            val output = getFakeLocationModalDataList()

            coEvery {
                connectionDetector.isInternetAvailable
            } returns true

            coEvery {
                forecastWeatherRemoteDataSource.getForecastWeather(any())
            } returns ErrorResult(message = errorMsg)

            coEvery {
                forecastWeatherLocalDataSource.getLocationList()
            } returns Success(output)

            val result = forecastWeatherRepository.getForeCastWeatherRepository(input).single()

            Truth.assertThat(result).isInstanceOf(Success::class.java)
            Truth.assertThat(result.get()!!).hasSize(4)

            coVerifySequence {
                connectionDetector.isInternetAvailable
                forecastWeatherRemoteDataSource.getForecastWeather(any())
                forecastWeatherLocalDataSource.getLocationList()
            }
        }

    @Test
    fun `empty weather data from localdb, if internet is available but failed to download data`() =
        coroutinesTestRule.runBlockingTest {

            val input = LocationRequestModal("London")
            val errorMsg = "Failed to hit server"
            val noRecord = "No internet connection"

            coEvery {
                connectionDetector.isInternetAvailable
            } returns true

            coEvery {
                forecastWeatherRemoteDataSource.getForecastWeather(any())
            } returns ErrorResult(message = errorMsg)

            coEvery {
                forecastWeatherLocalDataSource.getLocationList()
            } returns ErrorResult(message = noRecord)

            val result = forecastWeatherRepository.getForeCastWeatherRepository(input).single()

            Truth.assertThat(result).isInstanceOf(ErrorResult::class.java)
            Truth.assertThat((result as ErrorResult).message).isEqualTo(noRecord)

            coVerifySequence {
                connectionDetector.isInternetAvailable
                forecastWeatherRemoteDataSource.getForecastWeather(any())
                forecastWeatherLocalDataSource.getLocationList()
            }
        }

    @Test
    fun `get weather data from local db, if internet is not available`() =
        coroutinesTestRule.runBlockingTest {

            val input = LocationRequestModal("London")
            val output = getFakeLocationModalDataList()

            coEvery {
                connectionDetector.isInternetAvailable
            } returns false

            coEvery {
                forecastWeatherLocalDataSource.getLocationList()
            } returns Success(output)

            val result = forecastWeatherRepository.getForeCastWeatherRepository(input).single()

            Truth.assertThat(result).isInstanceOf(Success::class.java)
            Truth.assertThat(result.get()!!).hasSize(4)

            coVerifySequence {
                connectionDetector.isInternetAvailable
                forecastWeatherLocalDataSource.getLocationList()
            }
        }

    @Test
    fun `some errorOccured while getting data from localdb, on no internet available`() =
        coroutinesTestRule.runBlockingTest {

            val input = LocationRequestModal("London")
            val output = "An error occurred"

            coEvery {
                connectionDetector.isInternetAvailable
            } returns false

            coEvery {
                forecastWeatherLocalDataSource.getLocationList()
            } returns ErrorResult(message = output)

            val result = forecastWeatherRepository.getForeCastWeatherRepository(input).single()

            Truth.assertThat(result).isInstanceOf(ErrorResult::class.java)
            Truth.assertThat((result as ErrorResult).message).isEqualTo(output)

            coVerifySequence {
                connectionDetector.isInternetAvailable
                forecastWeatherLocalDataSource.getLocationList()
            }
        }
}