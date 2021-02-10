package reprator.willyWeather.cityList

import reprator.willyWeather.base.util.DateUtils
import reprator.willyWeather.cityList.datasource.remote.modal.*
import reprator.willyWeather.cityList.modals.LocationModal
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
                "London, UK", dateNextDay, epoch, "10:01 PM", "10:01 PM", "-1.8",
                "0.56", "89.01", "23.0", "Light Snow", "5.24",
                "78.0", "0.85", "91.0"
            ),

            LocationModal(
                "London, UK", dateDayAfterTomorrow, epoch, "10:01 PM", "10:01 PM", "-1.8",
                "0.56", "89.01", "23.0", "Light Snow", "5.24",
                "78.0", "0.85", "91.0"
            ),
        )
    }

    fun getFakeDBEntityDataList(): List<WeatherEntity> {
        val timezoneUK = getUKTimeZone()

        return listOf(
            WeatherEntity(
                "London",
                yesterday,
                dateUtils.convertToEpoch(0),

                dateUtils.format(
                    dateUtils.convertToEpoch(yesterdayRaw),
                    DateUtils.HOUR_MINUTE,
                    timezoneUK
                ),
                dateUtils.format(
                    dateUtils.convertToEpoch(yesterdayRaw),
                    DateUtils.HOUR_MINUTE,
                    timezoneUK
                ),

                "10",
                "23",
                "89.01",
                "32.90",
                "Foggy",
                "110.11",
                "98.11",
                "11.91",
                "118.00"
            ),
            WeatherEntity(
                "London",
                dateToday,
                dateUtils.convertToEpoch(0),

                dateUtils.format(
                    dateUtils.convertToEpoch(dateTodayRaw),
                    DateUtils.HOUR_MINUTE,
                    timezoneUK
                ),
                dateUtils.format(
                    dateUtils.convertToEpoch(dateTodayRaw),
                    DateUtils.HOUR_MINUTE,
                    timezoneUK
                ),


                "10",
                "23",
                "89.01",
                "32.90",
                "Foggy",
                "110.11",
                "98.11",
                "11.91",
                "118.00"
            ),
            WeatherEntity(
                "London",
                dateNextDay,
                dateUtils.convertToEpoch(0),

                dateUtils.format(
                    dateUtils.convertToEpoch(dateNextDayRaw),
                    DateUtils.HOUR_MINUTE,
                    timezoneUK
                ),
                dateUtils.format(
                    dateUtils.convertToEpoch(dateNextDayRaw),
                    DateUtils.HOUR_MINUTE,
                    timezoneUK
                ),

                "10",
                "23",
                "89.01",
                "32.90",
                "Foggy",
                "110.11",
                "98.11",
                "11.91",
                "118.00"
            ),
            WeatherEntity(
                "London",
                dateDayAfterTomorrow,
                dateUtils.convertToEpoch(0),

                dateUtils.format(
                    dateUtils.convertToEpoch(dateDayAfterTomorrowRaw),
                    DateUtils.HOUR_MINUTE,
                    timezoneUK
                ),
                dateUtils.format(
                    dateUtils.convertToEpoch(dateDayAfterTomorrowRaw),
                    DateUtils.HOUR_MINUTE,
                    timezoneUK
                ),

                "10",
                "23",
                "89.01",
                "32.90",
                "Foggy",
                "110.11",
                "98.11",
                "11.91",
                "118.00"
            )
        )
    }

    fun getFakeRemoteDataList(): ForecastLocationEntity {
        val cityEntity = CityRemoteEntity(
            243243, "London", CoordRemoteEntity(879.toDouble(), 72.toDouble()),
            "UK", 234234, 0
        )

        return ForecastLocationEntity(
            "200", 489, 4,
            listOf(
                ListRemoteEntity(
                    yesterdayRaw, yesterdayRaw, yesterdayRaw, TempRemoteEntity(
                        0.24, -1.8, 0.56, -1.28, -0.93, -1.69
                    ), FeelsLikeRemoteEntity(-5.52, -7.76, -6.99, -7.46),
                    23.toDouble(), 89.01, listOf(
                        WeatherRemoteEntity(
                            600, "Snow",
                            "Light Snow", "13d"
                        )
                    ), 5.24, 78.toDouble(), 91.toDouble(), 0.69, 0.85
                ),
                ListRemoteEntity(
                    dateTodayRaw, dateTodayRaw, dateTodayRaw, TempRemoteEntity(
                        0.24, -1.8, 0.56, -1.28, -0.93, -1.69
                    ), FeelsLikeRemoteEntity(-5.52, -7.76, -6.99, -7.46),
                    23.toDouble(), 89.01, listOf(
                        WeatherRemoteEntity(
                            600, "Snow",
                            "Light Snow", "13d"
                        )
                    ), 5.24, 78.toDouble(), 91.toDouble(), 0.69, 0.85
                )/*,
                ListRemoteEntity(
                    dateNextDayRaw, dateNextDayRaw, dateNextDayRaw, TempRemoteEntity(
                        0.24, -1.8, 0.56, -1.28, -0.93, -1.69
                    ), FeelsLikeRemoteEntity(-5.52, -7.76, -6.99, -7.46),
                    23.toDouble(), 89.01, listOf(
                        WeatherRemoteEntity(
                            600, "Snow",
                            "Light Snow", "13d"
                        )
                    ), 5.24, 78.toDouble(), 91.toDouble(), 0.69, 0.85
                ),
                ListRemoteEntity(
                    dateDayAfterTomorrowRaw,
                    dateDayAfterTomorrowRaw,
                    dateDayAfterTomorrowRaw,
                    TempRemoteEntity(
                        0.24, -1.8, 0.56, -1.28, -0.93, -1.69
                    ),
                    FeelsLikeRemoteEntity(-5.52, -7.76, -6.99, -7.46),
                    23.toDouble(),
                    89.01,
                    listOf(
                        WeatherRemoteEntity(
                            600, "Snow",
                            "Light Snow", "13d"
                        )
                    ),
                    5.24,
                    78.toDouble(),
                    91.toDouble(),
                    0.69,
                    0.85
                )*/
            ), cityEntity
        )
    }
}