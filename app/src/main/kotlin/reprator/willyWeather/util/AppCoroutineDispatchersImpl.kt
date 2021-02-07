package reprator.willyWeather.util

import kotlinx.coroutines.CoroutineDispatcher
import reprator.willyWeather.base.util.AppCoroutineDispatchers
import javax.inject.Inject

class AppCoroutineDispatchersImpl @Inject constructor(
    override val main: CoroutineDispatcher,
    override val computation: CoroutineDispatcher,
    override val io: CoroutineDispatcher,
    override val default: CoroutineDispatcher,
    override val singleThread: CoroutineDispatcher
) : AppCoroutineDispatchers