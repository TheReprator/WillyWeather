package reprator.willyWeather.util.connectivity

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.coroutineScope
import dagger.hilt.android.qualifiers.ApplicationContext
import reprator.willyWeather.base.util.ConnectionDetector
import reprator.willyWeather.util.connectivity.base.ConnectivityProvider
import javax.inject.Inject

class InternetChecker @Inject constructor(
    @ApplicationContext private val context: Context,
    lifecycle: Lifecycle, override var isInternetAvailable: Boolean = false
) : LifecycleObserver, ConnectionDetector, ConnectivityProvider.ConnectivityStateListener {

    private val provider: ConnectivityProvider by lazy { ConnectivityProvider.createProvider(context) }

    init {
        lifecycle.coroutineScope.launchWhenCreated {
            lifecycle.addObserver(this@InternetChecker)
        }
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        isInternetAvailable = state.hasInternet()
    }

    private fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
        return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onForegroundStartMonitoringConnectivityOnCreate() {
        provider.addListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForegroundStartMonitoringConnectivity() {
        provider.addListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackgroundStartMonitoringConnectivity() {
        provider.removeListener(this)
    }

}
