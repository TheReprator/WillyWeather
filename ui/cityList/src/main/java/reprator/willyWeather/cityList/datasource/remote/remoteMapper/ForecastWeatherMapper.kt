package reprator.willyWeather.cityList.datasource.remote.remoteMapper

import reprator.willyWeather.base.util.Mapper
import reprator.willyWeather.cityList.datasource.remote.modal.ForecastLocationEntity
import reprator.willyWeather.cityList.modals.LocationModal
import javax.inject.Inject

class ForecastWeatherMapper @Inject constructor() :
    Mapper<ForecastLocationEntity, List<LocationModal>> {

    override suspend fun map(from: ForecastLocationEntity): List<LocationModal> {
        return from.list.map {
            LocationModal(
                it.weather[0].description,
                it.main.temp.toString(),
                it.main.tempMin.toString(),
                it.main.tempMax.toString(),

                it.main.pressure.toString(),
                it.main.humidity.toString(),

                it.wind.speed.toString(),
                it.wind.deg.toString(),

                it.sys.sunrise.toString(),
                it.sys.sunset.toString()
            )
        }
    }
}
