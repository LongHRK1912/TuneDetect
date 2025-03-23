package com.hrk.apps.hrkdev.core.data

import androidx.lifecycle.ViewModel
import com.hrk.apps.hrkdev.core.model.iacr_cloud.IACRCloudState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TuneDetectViewModel @Inject constructor() : ViewModel() {
    private var _isServiceInitialized = MutableStateFlow(false)
    val isServiceInitialized = _isServiceInitialized.asStateFlow()

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

    fun handlerClicked(){
        when(_state.value){
            IACRCloudState.Nothing -> updateStateIACRCloud(
                IACRCloudState.Recording
            )
            else ->  updateStateIACRCloud(
                IACRCloudState.Nothing
            )
        }
    }
}