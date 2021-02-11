package reprator.willyWeather.cityList.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.cityList.TestFakeData.getFakeLocationModalDataList
import reprator.willyWeather.cityList.domain.repository.ForecastWeatherRepository
import reprator.willyWeather.cityList.modals.LocationRequestModal
import reprator.willyWeather.testUtils.MainCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ForecastWeatherUseCaseTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = MainCoroutineRule()

    @MockK
    lateinit var forecastWeatherRepository: ForecastWeatherRepository

    lateinit var forecastWeatherUseCase: ForecastWeatherUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)

        forecastWeatherUseCase = ForecastWeatherUseCase(forecastWeatherRepository)
    }

    @Test
    fun `fetch forecast data from local or remote data source`() = coroutinesTestRule.runBlockingTest {
        val input = LocationRequestModal("London")
        val output = getFakeLocationModalDataList()

        coEvery {
            forecastWeatherRepository.getForeCastWeatherRepository(any())
        } returns flowOf(Success(output))

        val result = forecastWeatherUseCase(input).single()

        Truth.assertThat(result).isInstanceOf(Success::class.java)
        Truth.assertThat((result as Success).data).isEqualTo(output)
    }
}