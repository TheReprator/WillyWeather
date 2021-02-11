package reprator.willyWeather.database

import reprator.willyWeather.base.util.DateUtils
import reprator.willyWeather.database.WeatherEntity
import reprator.willyWeather.testUtils.DateUtilsImpl

object DBFakeData {

    val dateUtils: DateUtils = DateUtilsImpl()

    val epochToDate = { epochTime: Long ->
        val date = dateUtils.getCalendar().time
        date.time = epochTime * 100
        date
    }

    val yesterday = epochToDate(1612780345L)
    val dateToday = epochToDate(1612872000L)
    val dateNextDay = epochToDate(1612958400L)
    val dateDayAfterTomorrow = epochToDate(1613044800L)

    fun getFakeDataList(): List<WeatherEntity> {
        val timezoneUK = dateUtils.convertToEpoch(0)
        return listOf(
            WeatherEntity(
                "London", yesterday, timezoneUK, "4:55 AM", "5:05 PM", "10",
                "23", "89.01", "32.90", "Foggy", "110.11",
                "98.11", "11.91", "118.00"),
            WeatherEntity(
                "London", dateToday, timezoneUK,  "5:15 AM", "6:15 PM", "10",
                "23", "89.01", "32.90", "Foggy", "110.11",
                "98.11", "11.91", "118.00"
            ),
            WeatherEntity(
                "London", dateNextDay, timezoneUK,  "6:12 AM", "6:30 PM", "10",
                "23", "89.01", "32.90", "Foggy", "110.11",
                "98.11", "11.91", "118.00"
            ),
            WeatherEntity(
                "London", dateDayAfterTomorrow,  timezoneUK, "7:21 AM", "7:30 PM", "10",
                "23", "89.01", "32.90", "Foggy", "110.11",
                "98.11", "11.91", "118.00"
            )
        )
    }
}