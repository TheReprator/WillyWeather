package reprator.willyWeather.city

import reprator.willyWeather.base.util.DateUtils
import reprator.willyWeather.city.modals.LocationModal
import reprator.willyWeather.database.WeatherEntity
import reprator.willyWeather.testUtils.DateUtilsImpl
import java.util.*

object TestFakeData {

    val dateUtils: DateUtils = DateUtilsImpl()

    val epochToDate = { epochTime: Long ->
        val date = dateUtils.getCalendar().time
        date.time = epochTime * 1000
        date
    }

    val yesterdayRaw = 1612780345L              //Monday, February 8, 2021 10:32:25 AM
    val yesterday = epochToDate(yesterdayRaw)

    val dateTodayRaw = 1612872000L
    val dateToday = epochToDate(dateTodayRaw)

    val dateNextDayRaw = 1612958400L
    val dateNextDay = epochToDate(dateNextDayRaw)

    val dateDayAfterTomorrowRaw = 1613044800L
    val dateDayAfterTomorrow = epochToDate(dateDayAfterTomorrowRaw)

    private fun getUKTimeZone(): TimeZone {
        return dateUtils.getTimeZone(getUKEpochTimeZone().toInt())
    }

    private fun getUKEpochTimeZone(): Long {
        return dateUtils.convertToEpoch(0)
    }

    fun getFakeLocationModalDataList(): List<LocationModal> {
        val timeZoneUk = getUKTimeZone()
        val epoch = getUKEpochTimeZone()

        return listOf(
            LocationModal(
                "London, UK", yesterday, epoch, "10:32 AM", "10:32 AM", "-1.8",
                "0.56", "89.01", "23.0", "Light Snow", "5.24",
                "78.0", "0.85", "91.0"
            ),

            LocationModal(
                "London, UK", dateToday, epoch, "12:00 PM", "12:00 PM", "-1.8",
                "0.56", "89.01", "23.0", "Light Snow", "5.24",
                "78.0", "0.85", "91.0"
            ),

            LocationModal(
                "London, UK", dateNextDay, epoch, "12:00 PM", "12:00 PM","-1.8",
                "0.56", "89.01", "23.0", "Light Snow", "5.24",
                "78.0", "0.85", "91.0"
            ),

            LocationModal(
                "London, UK", dateDayAfterTomorrow, epoch, "12:00 PM", "12:00 PM","-1.8",
                "0.56", "89.01", "23.0", "Light Snow", "5.24",
                "78.0", "0.85", "91.0"
            ),
        )
    }

    fun getFakeDBEntityDataList(): List<WeatherEntity> {
        val timeZoneUk = getUKTimeZone()
        val epoch = getUKEpochTimeZone()

        return listOf(
            WeatherEntity(
                "London, UK", yesterday, epoch, "10:32 AM", "10:32 AM", "-1.8",
                "0.56", "23.0", "89.01", "Light Snow", "5.24",
                "78.0", "0.85", "91.0"
            ),

            WeatherEntity(
                "London, UK", dateToday, epoch, "12:00 PM", "12:00 PM", "-1.8",
                "0.56", "23.0", "89.01", "Light Snow", "5.24",
                "78.0", "0.85", "91.0"
            ),

            WeatherEntity(
                "London, UK", dateNextDay, epoch, "12:00 PM", "12:00 PM","-1.8",
                "0.56", "23.0", "89.01",  "Light Snow", "5.24",
                "78.0", "0.85", "91.0"
            ),

            WeatherEntity(
                "London, UK", dateDayAfterTomorrow, epoch, "12:00 PM", "12:00 PM","-1.8",
                "0.56", "23.0", "89.01", "Light Snow", "5.24",
                "78.0", "0.85", "91.0"
            ),
        )
    }
}