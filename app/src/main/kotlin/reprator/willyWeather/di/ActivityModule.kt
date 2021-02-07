package reprator.willyWeather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import reprator.willyWeather.WillyNavigator
import reprator.willyWeather.navigation.AppNavigator

@InstallIn(ActivityComponent::class)
@Module
class ActivityModule {

    @Provides
    fun provideWillyNavigator(): AppNavigator {
        return WillyNavigator()
    }

}