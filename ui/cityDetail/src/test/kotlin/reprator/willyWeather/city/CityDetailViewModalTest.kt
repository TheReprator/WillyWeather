package reprator.willyWeather.city

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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
import reprator.willyWeather.city.TestFakeData.getFakeLocationModalDataList
import reprator.willyWeather.city.usecase.GetWeatherDetailUseCase
import reprator.willyWeather.testUtils.MainCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CityDetailViewModalTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = MainCoroutineRule()

    @MockK
    lateinit var getWeatherDetailUseCase: GetWeatherDetailUseCase

    @MockK
    lateinit var savedStateHandle: SavedStateHandle

    lateinit var cityDetailViewModal: CityDetailViewModal

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        cityDetailViewModal =
            CityDetailViewModal(
                savedStateHandle,
                coroutinesTestRule.testDispatcherProvider,
                getWeatherDetailUseCase
            )
    }

    @Test
    fun `get forecast for next 4 days`() = coroutinesTestRule.runBlockingTest {

        val output = getFakeLocationModalDataList()[0]

        coEvery {
            getWeatherDetailUseCase(any())
        } returns flowOf(Success(output))

        coEvery {
            savedStateHandle.get<Long>(any())
        } returns 99L

        Truth.assertThat(cityDetailViewModal.foreCastWeatherList.value).isNull()

        cityDetailViewModal.getWeatherDetailUseCase()

        Truth.assertThat(cityDetailViewModal.foreCastWeatherList.getOrAwaitValue())
            .isEqualTo(output)
    }
}