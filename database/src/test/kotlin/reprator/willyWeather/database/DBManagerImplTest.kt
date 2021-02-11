package reprator.willyWeather.database

import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.base.util.DateUtils
import reprator.willyWeather.database.DBFakeData.dateToday
import reprator.willyWeather.database.DBFakeData.getFakeDataList
import reprator.willyWeather.testUtils.MainCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DBManagerImplTest {

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var weatherDao: WeatherDao

    @MockK
    lateinit var dateUtils: DateUtils

    private lateinit var dbManager: DBManager

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        dbManager = DBManagerImpl(weatherDao, dateUtils)
    }

    @Test
    fun `No Records found`() = mainCoroutineRule.runBlockingTest {
        val input = emptyList<WeatherEntity>()
        coEvery {
            weatherDao.getWeatherList()
        } returns input

        val outputResult = dbManager.getWeatherList()

        Truth.assertThat(outputResult).isInstanceOf(Success::class.java)
        Truth.assertThat(outputResult.get()).hasSize(0)
    }

    @Test
    fun saveWeather() = mainCoroutineRule.runBlockingTest {
        val input = getFakeDataList()[0]
        val expectedOutput = 0L
        coEvery {
            weatherDao.insertWeather(input)
        } returns expectedOutput

        val outputResult = dbManager.saveWeather(input)

        Truth.assertThat(outputResult.get()).isEqualTo(expectedOutput)
    }

    @Test
    fun saveWeatherList() = mainCoroutineRule.runBlockingTest {
        val input = getFakeDataList()
        val expectedOutput = listOf(0L, 1L, 2L, 3L)
        coEvery {
            weatherDao.insertWeatherList(*input.toTypedArray())
        } returns expectedOutput

        val outputResult = dbManager.saveWeatherList(input)

        Truth.assertThat(outputResult.get()).isEqualTo(expectedOutput)
    }

    @Test
    fun getWeatherList() = mainCoroutineRule.runBlockingTest {
        val expectedOutput = getFakeDataList()
        coEvery {
            weatherDao.getWeatherList(any())
        } returns expectedOutput

        val outputResult = dbManager.getWeatherList()

        Truth.assertThat(outputResult.get()).isEqualTo(expectedOutput)
        Truth.assertThat(outputResult.get()).hasSize(4)
    }

    @Test
    fun getWeatherItem() = mainCoroutineRule.runBlockingTest {
        val expectedOutput = getFakeDataList()[0]
        coEvery {
            weatherDao.getWeatherItem(dateToday)
        } returns expectedOutput

        val outputResult = dbManager.getWeatherItem(dateToday)

        Truth.assertThat(outputResult.get()).isEqualTo(expectedOutput)
    }

    @Test
    fun deleteWeather() = mainCoroutineRule.runBlockingTest {
        val input = getFakeDataList()[0]
        val expectedOutput = 1
        coEvery {
            weatherDao.deleteWeather(input)
        } returns expectedOutput

        val outputResult = dbManager.deleteWeather(input)

        Truth.assertThat(outputResult.get()).isEqualTo(expectedOutput)
    }

    @Test
    fun clearTable() = mainCoroutineRule.runBlockingTest {
        val expectedOutput = 1
        coEvery {
            weatherDao.clearTable()
        } returns expectedOutput

        val outputResult = dbManager.clearTable()

        Truth.assertThat(outputResult.get()).isEqualTo(expectedOutput)
    }
}