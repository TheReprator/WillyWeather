package reprator.willyWeather.testUtils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import reprator.willyWeather.base.util.AppCoroutineDispatchers

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher(),
) : TestWatcher(),
    TestCoroutineScope by TestCoroutineScope(dispatcher) {

    val testDispatcherProvider = object : AppCoroutineDispatchers {
        override val main: CoroutineDispatcher get() = dispatcher

        override val default: CoroutineDispatcher get() = dispatcher

        override val computation: CoroutineDispatcher get() = dispatcher

        override val io: CoroutineDispatcher get() = dispatcher

        override val singleThread: CoroutineDispatcher get() = dispatcher
    }

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
        //        Dispatchers.setMain(this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

}