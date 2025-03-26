package com.hrk.apps.hrkdev.core.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrk.apps.hrkdev.core.data.repository.SearchingRepository
import com.hrk.apps.hrkdev.core.model.iacr_cloud.IACRCloudState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TuneDetectViewModel @Inject constructor(
    private val searchingRepository: SearchingRepository
) : ViewModel() {
    private var _isServiceInitialized = MutableStateFlow(false)
    val isServiceInitialized = _isServiceInitialized.asStateFlow()

    private var _token: String? =
        "BQDRy_PlmTnWxzNSP2X7XFgMPB9DBVY7oI8_Kq0v32Uu0HyIoDWXJ8YVCQQsGaOhphpq_mTm8xUBmFObBHSN6kAmvi3Pth3y9zKelc8YL4onxvDjDo1EaBGbP_IYYGSiV8YHduF6b7w"

    init {
        viewModelScope.launch {
//            searchingRepository.search().collect {
//                Log.d("ASDSADASDASDASD", "STATE $it")
//                when(it){
//                    is ResultWrapper.Success -> {
//
//                    }
//
//                    else ->  {}
//                }
//            }
        }
    }

    fun updateServiceInitialized(isInitialized: Boolean) {
        _isServiceInitialized.value = isInitialized
    }

    private val _volume = MutableStateFlow<Double>(0.0)
    val volume = _volume.asStateFlow()

    fun onVolumeChanged(volume: Double) {
        _volume.value = volume
    }

    private val _state = MutableStateFlow<IACRCloudState>(IACRCloudState.Nothing)
    val state = _state.asStateFlow()

    fun updateStateIACRCloud(newState: IACRCloudState) {
        _state.value = newState
    }

    fun resetStateIACRCloud() {
        _state.value = IACRCloudState.Nothing
    }

    fun handlerClicked() {
        when (_state.value) {
            IACRCloudState.Nothing -> updateStateIACRCloud(
                IACRCloudState.Recording
            )

            else -> updateStateIACRCloud(
                IACRCloudState.Nothing
            )
        }
    }
}