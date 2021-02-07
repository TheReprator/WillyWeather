package reprator.willyWeather.city

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import reprator.willyWeather.base.extensions.computationalBlock
import reprator.willyWeather.base.useCases.ErrorResult
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.base.util.AppCoroutineDispatchers
import reprator.willyWeather.city.modals.LocationModal
import reprator.willyWeather.city.usecase.ForecastWeatherUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CityDetailViewModal @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val coroutineDispatcherProvider: AppCoroutineDispatchers,
    private val forecastWeatherUseCase: ForecastWeatherUseCase,
) : ViewModel() {

    companion object {
        private const val LOCATION_ID = "id"
    }

    private val _foreCastWeatherList = MutableLiveData<LocationModal>()
    val foreCastWeatherList: LiveData<LocationModal> = _foreCastWeatherList

    fun getTodayWeatherUseCase() {
        computationalBlock {
            forecastWeatherUseCase(savedStateHandle.get<String>(LOCATION_ID)!!)
                .flowOn(coroutineDispatcherProvider.io).catch { e ->
                    Timber.e("error is ${e.localizedMessage}")
                }.flowOn(coroutineDispatcherProvider.main)
                .collect {
                    withContext(coroutineDispatcherProvider.main) {
                        when (it) {
                            is Success -> {
                                _foreCastWeatherList.value = it.get()
                            }
                            is ErrorResult -> {
                                Timber.e("error is ${it.message}")
                            }
                        }
                    }
                }
        }
    }

    private fun computationalBlock(
        coroutineExceptionHandler: CoroutineExceptionHandler? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.computationalBlock(
            coroutineDispatcherProvider,
            coroutineExceptionHandler,
            block
        )
    }
}