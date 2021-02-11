package reprator.willyWeather.implementation

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.base.util.DateUtils
import reprator.willyWeather.base.util.DateUtils.Companion.DD_H_MMM_H_YYYY_HOUR_MINUTE_SECONDS
import reprator.willyWeather.base.util.DateUtils.Companion.DD_MMM_YYYY
import reprator.willyWeather.base.util.DateUtils.Companion.DD_MMM_YYYY_HOUR_MINUTE_SECONDS
import java.util.*

@RunWith(JUnit4::class)
class DateUtilsImplTest {
    private val input = 1613286979L
    private val output ="14-Feb-2021 07:16:19 AM"

    private lateinit var dateUtil: DateUtils

    @Before
    fun setup(){
        dateUtil = DateUtilsImpl()
    }

    @Test
    fun getTimeZone() {
        val output = "Africa/Abidjan"

        val result = dateUtil.getTimeZone(dateUtil.convertToEpoch(0).toInt())
        Truth.assertThat(result.id).isEqualTo(output)
    }

    @Test
    fun convertToEpoch() {
        val input = 19800
        val output = 19800 * 1000L

        val result = dateUtil.convertToEpoch(input.toLong())
        Truth.assertThat(result).isEqualTo(output)
    }

    @Test
    fun formatWithTimeZone() {
        val timeZone = dateUtil.getTimeZone(dateUtil.convertToEpoch(0).toInt())
        val result = dateUtil.format(dateUtil.convertToEpoch(input), DD_MMM_YYYY_HOUR_MINUTE_SECONDS, timeZone)
        Truth.assertThat(result).isEqualTo(output)
    }

    @Test
    fun formatWithDefaultTimeZone() {
        val output = "14-Feb-2021 12:46:19 PM"
        val istZone = TimeZone.getTimeZone("Asia/Calcutta")
        val result = dateUtil.format(dateUtil.convertToEpoch(input), DD_MMM_YYYY_HOUR_MINUTE_SECONDS, istZone)
        Truth.assertThat(result).isEqualTo(output)
    }

    @Test
    fun formatDateWithTimeZone() {
        val date = dateUtil.getCalendar()
        date.timeInMillis = dateUtil.convertToEpoch(input)

        val timeZone = dateUtil.getTimeZone(dateUtil.convertToEpoch(0).toInt())

        val result = dateUtil.format(date.timeInMillis, DD_MMM_YYYY_HOUR_MINUTE_SECONDS, timeZone)
        Truth.assertThat(result).isEqualTo(output)
    }

    @Test
    fun formatDateWithDefaultISTTimeZone() {
        val output = "14-Feb-2021 12:46:19 PM"

        val date = dateUtil.getCalendar()
        date.timeInMillis = dateUtil.convertToEpoch(input)

        val istZone = TimeZone.getTimeZone("Asia/Calcutta")

        val result = dateUtil.format(date.timeInMillis, DD_MMM_YYYY_HOUR_MINUTE_SECONDS, istZone)
        Truth.assertThat(result).isEqualTo(output)
    }

    @Test
    fun convertFromOneFormatToAnotherWithTimeZone() {
        val input = "14-Feb-2021 12:46:19 PM"
        val output = "14/Feb/2021 12:46:19 PM"

        val timeZone = dateUtil.getTimeZone(dateUtil.convertToEpoch(0).toInt())
        val result = dateUtil.format(input, DD_MMM_YYYY_HOUR_MINUTE_SECONDS,
                DD_H_MMM_H_YYYY_HOUR_MINUTE_SECONDS, timeZone.id)

        Truth.assertThat(result).isEqualTo(output)
    }

    @Test
    fun convertFromOneFormatToAnotherWithDefaultTimeZone() {
        val input = "14-Feb-2021 12:46:19 PM"
        val output = "14/Feb/2021 12:46:19 PM"

        val result = dateUtil.format(input, DD_MMM_YYYY_HOUR_MINUTE_SECONDS,
                DD_H_MMM_H_YYYY_HOUR_MINUTE_SECONDS)

        Truth.assertThat(result).isEqualTo(output)
    }

    @Test
    fun parseDateWithDefaultISTTimeZone() {
        val output = "14-Feb-2021 12:46:19 PM"

        val date = dateUtil.getCalendar()
        date.timeInMillis = dateUtil.convertToEpoch(input)

        val result = dateUtil.format(date.timeInMillis, DD_MMM_YYYY_HOUR_MINUTE_SECONDS)
        Truth.assertThat(result).isEqualTo(output)
    }

    @Test
    fun parseDateWithGivenTimeZone() {
        val date = dateUtil.getCalendar()
        date.timeInMillis = dateUtil.convertToEpoch(input)

        val timeZone = dateUtil.getTimeZone(dateUtil.convertToEpoch(0).toInt())

        val result = dateUtil.format(date.timeInMillis, DD_MMM_YYYY_HOUR_MINUTE_SECONDS, timeZone)
        Truth.assertThat(result).isEqualTo(output)
    }

    @Test
    fun parseAndFormatDateWithGivenTimeZone() {
        val timeZone = dateUtil.getTimeZone(dateUtil.convertToEpoch(0).toInt())

        val input = 1612780345L            // "Monday, February 8, 2021 10:32:25 AM"

        val output = dateUtil.getCalendar()
        output.timeInMillis = dateUtil.convertToEpoch(input)
        output.timeZone = timeZone


       val result = dateUtil.parse(
           dateUtil.format(
               dateUtil.convertToEpoch(input),
                DD_MMM_YYYY_HOUR_MINUTE_SECONDS,
               timeZone
            ), DD_MMM_YYYY_HOUR_MINUTE_SECONDS, timeZone
        )!!

        Truth.assertThat(result).isEqualTo(output.time)
    }


    @Test
    fun getTimeWithTimeZone() {
        val input = 1612780345L
        val output ="10:32 AM"

        val timeZone = dateUtil.getTimeZone(dateUtil.convertToEpoch(0).toInt())

        val result = dateUtil.format(dateUtil.convertToEpoch(input), DateUtils.HOUR_MINUTE, timeZone)
        Truth.assertThat(result).isEqualTo(output)
    }

    @Ignore
    @Test
    fun getCalendar() {
    }

    @Ignore
    @Test
    fun getFieldFromCalendar() {
    }
}