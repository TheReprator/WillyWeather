package reprator.willyWeather.cityList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.androidTest.util.getOrAwaitValue
import reprator.willyWeather.androidTest.util.observeForTesting
import reprator.willyWeather.base.useCases.ErrorResult
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.cityList.TestFakeData.getFakeLocationModalDataList
import reprator.willyWeather.cityList.domain.usecase.ForecastWeatherUseCase
import reprator.willyWeather.testUtils.MainCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CityListViewModalTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = MainCoroutineRule()

    @MockK
    lateinit var forecastWeatherUseCase: ForecastWeatherUseCase

    lateinit var cityListViewModal: CityListViewModal

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        cityListViewModal =
            CityListViewModal(coroutinesTestRule.testDispatcherProvider, forecastWeatherUseCase)
    }

    @Test
    fun `get forecast for next 4 days`() = coroutinesTestRule.runBlockingTest {

        val output = getFakeLocationModalDataList()

        coEvery {
            forecastWeatherUseCase(any())
        } returns flowOf(Success(output))

        coroutinesTestRule.pauseDispatcher()

        cityListViewModal.getForeCastWeatherUse()

        cityListViewModal._foreCastWeatherList.observeForTesting {
            Truth.assertThat(cityListViewModal.isLoadingForeCast.getOrAwaitValue()).isTrue()
            Truth.assertThat(cityListViewModal.errorMsgForeCast.getOrAwaitValue()).isEmpty()

            coroutinesTestRule.resumeDispatcher()

            Truth.assertThat(cityListViewModal.isLoadingForeCast.getOrAwaitValue()).isFalse()

            Truth.assertThat(cityListViewModal._foreCastWeatherList.getOrAwaitValue()).hasSize(4)
            Truth.assertThat(cityListViewModal._foreCastWeatherList.getOrAwaitValue())
                .isEqualTo(output)
        }
    }

    @Test
    fun `error on fetching forecast for next 4 days`() = coroutinesTestRule.runBlockingTest {

        val output = "An error occurred"

        coEvery {
            forecastWeatherUseCase(any())
        } returns flowOf(ErrorResult(message = output))

        coroutinesTestRule.pauseDispatcher()

        cityListViewModal.getForeCastWeatherUse()

        cityListViewModal._foreCastWeatherList.observeForTesting {
            Truth.assertThat(cityListViewModal.isLoadingForeCast.getOrAwaitValue()).isTrue()
            Truth.assertThat(cityListViewModal.errorMsgForeCast.getOrAwaitValue()).isEmpty()

            coroutinesTestRule.resumeDispatcher()

            Truth.assertThat(cityListViewModal.isLoadingForeCast.getOrAwaitValue()).isFalse()
            Truth.assertThat(cityListViewModal.errorMsgForeCast.getOrAwaitValue()).isEqualTo(output)

            Truth.assertThat(cityListViewModal._foreCastWeatherList.getOrAwaitValue()).isEmpty()
        }
    }

    @Test
    fun `retry, after error occurred on fetching forecast for next 4days`() =
        coroutinesTestRule.runBlockingTest {

            val output = getFakeLocationModalDataList()

            coEvery {
                forecastWeatherUseCase(any())
            } returns flowOf(Success(output))

            coroutinesTestRule.pauseDispatcher()

            cityListViewModal.retryForeCastWeather()

            cityListViewModal._foreCastWeatherList.observeForTesting {
                Truth.assertThat(cityListViewModal.isLoadingForeCast.getOrAwaitValue()).isTrue()
                Truth.assertThat(cityListViewModal.errorMsgForeCast.getOrAwaitValue()).isEmpty()

                coroutinesTestRule.resumeDispatcher()

                Truth.assertThat(cityListViewModal.isLoadingForeCast.getOrAwaitValue()).isFalse()

                Truth.assertThat(cityListViewModal._foreCastWeatherList.getOrAwaitValue())
                    .hasSize(4)
                Truth.assertThat(cityListViewModal._foreCastWeatherList.getOrAwaitValue())
                    .isEqualTo(output)
            }
        }
}