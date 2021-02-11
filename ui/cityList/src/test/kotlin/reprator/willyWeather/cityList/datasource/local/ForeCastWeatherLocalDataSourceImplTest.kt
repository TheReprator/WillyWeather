package reprator.willyWeather.cityList.datasource.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.cityList.TestFakeData.getFakeDBEntityDataList
import reprator.willyWeather.cityList.TestFakeData.getFakeLocationModalDataList
import reprator.willyWeather.cityList.data.datasource.ForecastWeatherLocalDataSource
import reprator.willyWeather.cityList.datasource.local.mapper.GetWeatherListMapper
import reprator.willyWeather.cityList.modals.LocationModal
import reprator.willyWeather.database.DBManager
import reprator.willyWeather.database.WeatherEntity
import reprator.willyWeather.testUtils.MainCoroutineRule

@RunWith(JUnit4::class)
class ForeCastWeatherLocalDataSourceImplTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = MainCoroutineRule()

    @MockK
    lateinit var dbManager: DBManager

    @MockK
    lateinit var getWeatherListMapper: GetWeatherListMapper

    private lateinit var forecastWeatherLocalDataSource: ForecastWeatherLocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, true)

        forecastWeatherLocalDataSource = ForeCastWeatherLocalDataSourceImpl(
            dbManager,
            getWeatherListMapper
        )
    }

    @Test
    fun `get records from dataSource`() = coroutinesTestRule.runBlockingTest {

        val input = listOf(getFakeDBEntityDataList()[0])
        val output = listOf(getFakeLocationModalDataList()[0])

        coEvery {
            dbManager.getWeatherList()
        } returns Success(input)

        coEvery {
            getWeatherListMapper.mapTo(any())
        } returns output[0]

        val result = forecastWeatherLocalDataSource.getLocationList()

        Truth.assertThat(result).isInstanceOf(Success::class.java)
        Truth.assertThat((result as Success).data).isEqualTo(output)
        Truth.assertThat(result.data).hasSize(1)
    }

    @Test
    fun `no records exist in dataSource`() = coroutinesTestRule.runBlockingTest {

        val input = emptyList<WeatherEntity>()
        val output = emptyList<LocationModal>()

        coEvery {
            dbManager.getWeatherList()
        } returns Success(input)

        coEvery {
            getWeatherListMapper.mapTo(any())
        } returns mockk(relaxed = true)

        val result = forecastWeatherLocalDataSource.getLocationList()

        Truth.assertThat(result).isInstanceOf(Success::class.java)
        Truth.assertThat((result as Success).data).isEqualTo(output)
        Truth.assertThat(result.data).isEmpty()
    }

    @Test
    fun `clear all records from dataSource`() = coroutinesTestRule.runBlockingTest {

        val input = 1
        val output = 1

        coEvery {
            dbManager.clearTable()
        } returns Success(input)

        val result = forecastWeatherLocalDataSource.clearAllRecords()

        Truth.assertThat(result).isInstanceOf(Success::class.java)
        Truth.assertThat((result as Success).data).isEqualTo(output)
    }

    @Test
    fun `insert list of records in dataSource`() = coroutinesTestRule.runBlockingTest {

        val mapInput = getFakeDBEntityDataList()[0]
        val input = getFakeLocationModalDataList()
        val output = listOf(1L,2L,3L,4L)

        coEvery {
            dbManager.saveWeatherList(any())
        } returns Success(output)

        coEvery {
            getWeatherListMapper.mapIn(any())
        } returns mapInput

        val result = forecastWeatherLocalDataSource.insertAllRecords(input)

        Truth.assertThat(result).isInstanceOf(Success::class.java)
        Truth.assertThat((result as Success).data).isEqualTo(output)
        Truth.assertThat(result.data).hasSize(4)
    }
}