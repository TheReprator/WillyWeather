package reprator.willyWeather.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.coroutineScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import reprator.willyWeather.R
import reprator.willyWeather.base.util.AppCoroutineDispatchers
import reprator.willyWeather.base.util.ConnectionDetector
import reprator.willyWeather.navigation.AppNavigator
import reprator.willyWeather.util.AppCoroutineDispatchersImpl
import reprator.willyWeather.util.connectivity.InternetChecker
import java.util.concurrent.Executors
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideAppPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
    }

    @Provides
    fun provideLifeCycle(): Lifecycle {
        return ProcessLifecycleOwner.get().lifecycle
    }

    @Provides
    fun provideLifetimeScope(lifecycle: Lifecycle): CoroutineScope {
        return lifecycle.coroutineScope
    }

    @Provides
    fun provideCoroutineDispatcherProvider(): AppCoroutineDispatchers {
        return AppCoroutineDispatchersImpl(
                Dispatchers.Main, Dispatchers.IO, Dispatchers.IO, Dispatchers.Default,
                Executors.newFixedThreadPool(1).asCoroutineDispatcher()
        )
    }

    @Singleton
    @Provides
    fun provideConnectivityChecker(
            @ApplicationContext context: Context, lifecycle: Lifecycle
    ): ConnectionDetector {
        return InternetChecker(context, lifecycle)
    }
}