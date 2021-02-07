package reprator.willyWeather.city.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import reprator.willyWeather.navigation.AppNavigator
import reprator.willyWeather.navigation.CityDetailNavigator

@InstallIn(FragmentComponent::class)
@Module
class CityDetailModule {

    @Provides
    fun provideCityDetailNavigator(appNavigator: AppNavigator
    ): CityDetailNavigator {
        return appNavigator
    }

}
