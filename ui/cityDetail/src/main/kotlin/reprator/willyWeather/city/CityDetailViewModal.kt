package reprator.willyWeather.city

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
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
import reprator.willyWeather.city.usecase.GetWeatherDetailUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CityDetailViewModal @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val coroutineDispatcherProvider: AppCoroutineDispatchers,
    private val getWeatherDetailUseCase: GetWeatherDetailUseCase,
) : ViewModel() {

    companion object {
        private const val LOCATION_ID = "id"
    }

    private val _forecastWeatherItem = MutableLiveData<LocationModal>()
    val foreCastWeatherList: LiveData<LocationModal> = _forecastWeatherItem

    fun getWeatherDetailUseCase() {
        computationalBlock {
            getWeatherDetailUseCase(savedStateHandle.get<Long>(LOCATION_ID)!!)
                .flowOn(coroutineDispatcherProvider.io).catch { e ->
                    Timber.e("error is ${e.localizedMessage}")
                }.flowOn(coroutineDispatcherProvider.main)
                .collect {
                    withContext(coroutineDispatcherProvider.main) {
                        when (it) {
                            is Success -> {
                                _forecastWeatherItem.value = it.get()
                            }
                            is ErrorResult -> {
                                Timber.e("error is ${it.message}")
                            }
                            else -> throw IllegalStateException()
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