package reprator.willyWeather.cityList.datasource.remote.remoteMapper

import reprator.willyWeather.base.util.DateUtils
import reprator.willyWeather.base.util.DateUtils.Companion.DD_MMM_YYYY_HOUR_MINUTE_SECONDS
import reprator.willyWeather.base.util.DateUtils.Companion.HOUR_MINUTE
import reprator.willyWeather.base.util.Mapper
import reprator.willyWeather.cityList.datasource.remote.modal.ForecastLocationEntity
import reprator.willyWeather.cityList.modals.LocationModal
import javax.inject.Inject

class ForecastWeatherMapper @Inject constructor(private val dateUtils: DateUtils) :
        Mapper<ForecastLocationEntity, List<LocationModal>> {

    override suspend fun map(from: ForecastLocationEntity): List<LocationModal> {
        val timeZone = dateUtils.convertToEpoch(from.city.timezone.toLong())
        val timeZoneEpoch = dateUtils.getTimeZone(timeZone.toInt())
        return from.list.map {
            LocationModal(
                    "${from.city.name}, ${from.city.country}",
                    dateUtils.parse(
                            dateUtils.format(
                                    dateUtils.convertToEpoch(it.dt),
                                    DD_MMM_YYYY_HOUR_MINUTE_SECONDS,
                                    timeZoneEpoch
                            ), DD_MMM_YYYY_HOUR_MINUTE_SECONDS, timeZoneEpoch
                    )!!,

                    timeZone,
                    dateUtils.format(dateUtils.convertToEpoch(it.sunset), HOUR_MINUTE, timeZoneEpoch),
                    dateUtils.format(dateUtils.convertToEpoch(it.sunrise), HOUR_MINUTE, timeZoneEpoch),

                    it.temp.min.toString(),
                    it.temp.max.toString(),

                    it.humidity.toString(),
                    it.pressure.toString(),

                    it.weather[0].description,

                    it.speed.toString(),
                    it.deg.toString(),

                    it.snow.toString(),
                    it.clouds.toString()
            )
        }
    }
}