package reprator.willyWeather.city

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.willyWeather.testUtils.MainCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CityDetailViewModalTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = MainCoroutineRule()

    @Before
    fun setUp() = MockKAnnotations.init(this)
}