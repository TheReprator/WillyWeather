package reprator.willyWeather

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import reprator.willyWeather.DBFakeData.dateDayAfterTomorrow
import reprator.willyWeather.DBFakeData.dateNextDay
import reprator.willyWeather.DBFakeData.dateToday
import reprator.willyWeather.DBFakeData.getFakeDataList
import reprator.willyWeather.DBFakeData.yesterday
import reprator.willyWeather.database.WeatherDao
import reprator.willyWeather.implementation.WillyWeatherRoomDb
import reprator.willyWeather.testUtils.MainCoroutineRule
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class WillyWeatherRoomDaoTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var db: WillyWeatherRoomDb
    lateinit var weatherDao: WeatherDao

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, WillyWeatherRoomDb::class.java
        ).build()
        weatherDao = db.weatherDao()
    }

    @Test
    fun saveListAndFetchWeatherItemForDate_1612872000000() = mainCoroutineRule.runBlockingTest {

        val weatherListOutput = getFakeDataList()

        weatherDao.insertWeatherList(*weatherListOutput.toTypedArray())

        val weatherItem = weatherDao.getWeatherItem(dateToday)

        Truth.assertThat(weatherItem).isEqualTo(weatherListOutput[1])
        Truth.assertThat(weatherItem.weatherDate.time).isEqualTo(dateToday.time)
    }

    @Test
    fun saveListAndFetchWeatherListGreaterThan1612780345000() = mainCoroutineRule.runBlockingTest {

        val weatherListOutput = getFakeDataList()
        weatherDao.insertWeatherList(*weatherListOutput.toTypedArray())

        val weatherItemList = weatherDao.getWeatherList(yesterday)

        Truth.assertThat(weatherItemList).hasSize(3)
        Truth.assertThat(weatherItemList[0].weatherDate.time).isEqualTo(dateToday.time)
        Truth.assertThat(weatherItemList[1].weatherDate.time).isEqualTo(dateNextDay.time)
        Truth.assertThat(weatherItemList[2].weatherDate.time).isEqualTo(dateDayAfterTomorrow.time)
    }

    @Test
    fun insertAndFetchWeather1612780345000() = mainCoroutineRule.runBlockingTest {

        val weatherItem = getFakeDataList()[0]

        weatherDao.insertWeather(weatherItem)

        val weatherItemList = weatherDao.getWeatherItem(yesterday)

        Truth.assertThat(weatherItemList).isEqualTo(weatherItem)
        Truth.assertThat(weatherItemList.weatherDate).isEqualTo(yesterday)
    }

    @Test
    fun saveListAndDeleteWeatherItem1612780345000() = mainCoroutineRule.runBlockingTest {
        val weatherListOutput = getFakeDataList()
        weatherDao.insertWeatherList(*weatherListOutput.toTypedArray())

        val result = weatherDao.deleteWeather(weatherListOutput[0])
        Truth.assertThat(result).isEqualTo(1)
    }

    @Test
    fun saveListAndClearTable() = mainCoroutineRule.runBlockingTest {
        val weatherListOutput = getFakeDataList()
        weatherDao.insertWeatherList(*weatherListOutput.toTypedArray())

        val deleteResult = weatherDao.clearTable()
        Truth.assertThat(deleteResult).isEqualTo(4)

        val result = weatherDao.getWeatherList()
        Truth.assertThat(result).hasSize(0)
    }
}