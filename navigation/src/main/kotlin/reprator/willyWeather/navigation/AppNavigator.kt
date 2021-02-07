package reprator.willyWeather.navigation

import androidx.navigation.NavController

interface AppNavigator: CityListNavigator, CityDetailNavigator

interface CityListNavigator {
    fun navigateToCityDetailScreen(navController: NavController, id: String)
}

interface CityDetailNavigator: BackNavigator

interface BackNavigator{
    fun navigateToBack(navController: NavController)
}