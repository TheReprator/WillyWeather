package reprator.willyWeather.base.util

interface AppNavigator: HomeNavigator, SavedLocationNavigator, CityDetailNavigator

interface HomeNavigator {
    fun navigateToAddLocation()
    fun navigateToSavedLocation()
}

interface SavedLocationNavigator{
    fun navigateToCityDetail()
}

interface CityDetailNavigator: BackNavigator

interface BackNavigator{
    fun navigateToBack()
}