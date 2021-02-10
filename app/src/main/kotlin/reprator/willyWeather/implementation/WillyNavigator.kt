package reprator.willyWeather.implementation

import androidx.navigation.NavController
import reprator.willyWeather.cityList.ui.CityListFragmentDirections
import reprator.willyWeather.navigation.AppNavigator
import javax.inject.Inject

class WillyNavigator @Inject constructor(): AppNavigator {

    override fun navigateToCityDetailScreen(
        navController: NavController,
        id: Long,
        placeName: String
    ) {
        val direction =
            CityListFragmentDirections.nnavigationHomeListToNavigationCityDetail(id, placeName)
        navController.navigate(direction)
    }

    override fun navigateToBack(navController: NavController) {
        navController.navigateUp()
    }
}