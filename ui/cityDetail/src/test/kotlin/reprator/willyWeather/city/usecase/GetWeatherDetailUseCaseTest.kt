package reprator.willyWeather.city.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.city.TestFakeData.getFakeDBEntityDataList
import reprator.willyWeather.city.TestFakeData.getFakeLocationModalDataList
import reprator.willyWeather.city.TestFakeData.yesterday
import reprator.willyWeather.database.DBManager
import reprator.willyWeather.testUtils.MainCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetWeatherDetailUseCaseTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = MainCoroutineRule()

    @MockK
    lateinit var dbManager: DBManager

    @MockK
    lateinit var getWeatherDetailMapper: GetWeatherDetailMapper

    lateinit var forecastWeatherUseCase: GetWeatherDetailUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)

        forecastWeatherUseCase = GetWeatherDetailUseCase(dbManager, getWeatherDetailMapper)
    }

    @Test
    fun `fetch forecast data from local or remote data source`() =
        coroutinesTestRule.runBlockingTest {
            val output = getFakeLocationModalDataList()[0]
            val input = getFakeDBEntityDataList()[0]

            coEvery {
                dbManager.getWeatherItem(any())
            } returns Success(input)

            coEvery {
                getWeatherDetailMapper.map(input)
            } returns output

            val result = forecastWeatherUseCase(yesterday.time).single()

            Truth.assertThat(result).isInstanceOf(Success::class.java)
            Truth.assertThat((result as Success).data).isEqualTo(output)
        }
}