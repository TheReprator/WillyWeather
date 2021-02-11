package reprator.willyWeather.cityList.datasource.local.mapper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.spyk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.cityList.TestFakeData


@RunWith(JUnit4::class)
class GetWeatherListMapperTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `create the map from WeatherEntity to LocationModal class`() = runBlockingTest {
        val input = TestFakeData.getFakeDBEntityDataList()[0]
        val output = TestFakeData.getFakeLocationModalDataList()[0]

        val mapper = spyk(GetWeatherListMapper())

        val result = mapper.mapTo(input)

        Truth.assertThat(output).isEqualTo(result)

        coVerify(atMost = 1) { mapper.mapTo(input) }

        confirmVerified(mapper)
    }

    @Test
    fun `create the map from LocationModal to WeatherEntityclass`() = runBlockingTest {
        val output = TestFakeData.getFakeDBEntityDataList()[0]
        val input = TestFakeData.getFakeLocationModalDataList()[0]

        val mapper = spyk(GetWeatherListMapper())

        val result = mapper.mapIn(input)

        Truth.assertThat(output).isEqualTo(result)

        coVerify(atMost = 1) { mapper.mapIn(input) }

        confirmVerified(mapper)
    }
}