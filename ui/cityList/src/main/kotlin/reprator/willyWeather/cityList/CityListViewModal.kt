package reprator.willyWeather.cityList

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import reprator.willyWeather.base.extensions.computationalBlock
import reprator.willyWeather.base.extensions.mainBlock
import reprator.willyWeather.base.useCases.ErrorResult
import reprator.willyWeather.base.useCases.Success
import reprator.willyWeather.base.util.AppCoroutineDispatchers
import reprator.willyWeather.cityList.domain.usecase.ForecastWeatherUseCase
import reprator.willyWeather.cityList.modals.LocationModal
import reprator.willyWeather.cityList.modals.LocationRequestModal
import javax.inject.Inject

@HiltViewModel
class CityListViewModal @Inject constructor(
        private val coroutineDispatcherProvider: AppCoroutineDispatchers,
        private val forecastWeatherUseCase: ForecastWeatherUseCase
) : ViewModel() {

    private val _isLoadingForeCast = MutableLiveData(true)
    val isLoadingForeCast: LiveData<Boolean> = _isLoadingForeCast

    private val _errorMsgForeCast = MutableLiveData("")
    val errorMsgForeCast: LiveData<String> = _errorMsgForeCast

    @VisibleForTesting
    val _foreCastWeatherList = MutableLiveData(emptyList<LocationModal>())

    private val errorHandler = CoroutineExceptionHandler { _, error ->
        mainBlock {
            error.printStackTrace()
            _errorMsgForeCast.value = error.localizedMessage
            _isLoadingForeCast.value = false
        }
    }

    fun getForeCastWeatherUse() {

        computationalBlock(errorHandler) {
            forecastWeatherUseCase(createRequestModal()).flowOn(coroutineDispatcherProvider.io)
                    .catch { e ->
                        e.printStackTrace()
                        _errorMsgForeCast.value = e.localizedMessage
                    }.onStart {
                        _isLoadingForeCast.value = true
                    }.onCompletion {
                        _isLoadingForeCast.value = false
                    }.flowOn(coroutineDispatcherProvider.main)
                    .collect {
                        withContext(coroutineDispatcherProvider.main) {
                            when (it) {
                                is Success -> {
                                    _foreCastWeatherList.value = it.data
                                }
                                is ErrorResult -> {
                                    _errorMsgForeCast.value = it.message!!
                                }
                            }
                        }
                    }
        }
    }

    fun retryForeCastWeather() {
        _errorMsgForeCast.value = ""
        _isLoadingForeCast.value = true
        getForeCastWeatherUse()
    }

    private fun createRequestModal(): LocationRequestModal {
        //val latLng = savedStateHandle.get<String>("latLng")!!.split(",")
        return LocationRequestModal("london")
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

    private fun mainBlock(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.mainBlock(coroutineDispatcherProvider, block)
    }
}