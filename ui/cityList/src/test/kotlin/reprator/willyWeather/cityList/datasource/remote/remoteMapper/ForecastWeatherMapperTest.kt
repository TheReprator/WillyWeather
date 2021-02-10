package reprator.willyWeather.cityList.datasource.remote.remoteMapper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.*
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.base.util.DateUtils
import reprator.willyWeather.cityList.TestFakeData.getFakeLocationModalDataList
import reprator.willyWeather.cityList.TestFakeData.getFakeRemoteDataList
import reprator.willyWeather.cityList.TestFakeData.yesterdayRaw
import reprator.willyWeather.testUtils.DateUtilsImpl

@RunWith(JUnit4::class)
class ForecastWeatherMapperTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    val dateUtils: DateUtilsImpl = DateUtilsImpl()

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `create the map into Rate list pojo class`() = runBlockingTest {
        val input = getFakeRemoteDataList()
        val output = getFakeLocationModalDataList()

        val mapper = spyk(ForecastWeatherMapper(dateUtils))

        val result = mapper.map(input)

        Truth.assertThat(output).isEqualTo(result)

        coVerify(atMost = 1) { mapper.map(input) }

        confirmVerified(mapper)
    }
}