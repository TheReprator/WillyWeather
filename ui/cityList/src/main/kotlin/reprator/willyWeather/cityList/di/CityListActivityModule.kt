package reprator.willyWeather.cityList.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import reprator.willyWeather.navigation.AppNavigator
import reprator.willyWeather.navigation.CityListNavigator

@InstallIn(FragmentComponent::class)
@Module
class CityListActivityModule {

    @Provides
    fun provideCityListNavigator(appNavigator: AppNavigator
    ): CityListNavigator {
        return appNavigator
    }
}