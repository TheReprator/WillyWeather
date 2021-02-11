package reprator.willyWeather.city.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.city.TestFakeData
import reprator.willyWeather.testUtils.MainCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetWeatherDetailUseCaseMapperTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = MainCoroutineRule()

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `create the map from WeatherEntity to LocationModal class`() = runBlockingTest {
        val input = TestFakeData.getFakeDBEntityDataList()[0]
        val output = TestFakeData.getFakeLocationModalDataList()[0]

        val mapper = spyk(GetWeatherDetailMapper())

        val result = mapper.map(input)

        Truth.assertThat(output).isEqualTo(result)

        coVerify(atMost = 1) { mapper.map(input) }

        confirmVerified(mapper)
    }
}