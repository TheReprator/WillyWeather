package reprator.willyWeather.cityList.ui

import reprator.willyWeather.cityList.modals.LocationModal

interface CityItemClickListener {
    fun itemClicked(locationModal: LocationModal)
}