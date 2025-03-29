package com.hrk.apps.hrkdev.core.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrk.apps.hrkdev.core.data.repository.SearchingRepository
import com.hrk.apps.hrkdev.core.model.iacr_cloud.IACRCloudState
import com.hrk.apps.hrkdev.core.model.spotify.SearchSpotifyBody
import com.hrk.apps.hrkdev.core.model.spotify.SearchSpotifyResponse
import com.hrk.apps.hrkdev.core.utils.JSON.decodeTo
import com.hrk.apps.hrkdev.core.utils.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TuneDetectViewModel @Inject constructor(
    private val searchingRepository: SearchingRepository
) : ViewModel() {
    private var _isServiceInitialized = MutableStateFlow(false)
    val isServiceInitialized = _isServiceInitialized.asStateFlow()

    private var _token: String? = null
    var spotifyResponse = MutableStateFlow<SearchSpotifyResponse?>(null)

    init {
        viewModelScope.launch {
//            if (_token == null) {
//                Log.d("ASDSADASDASDASD", "Token")
//                searchingRepository.auth().collect {
//                    Log.d("ASDSADASDASDASD", it.toJson())
//                    when (it) {
//                        is ResultWrapper.Success -> {
//                            Log.d("ASDSADASDASDASD", "Token ${it.value.access_token}")
//                            _token = it.value.access_token
//                        }
//
//                        else -> {}
//                    }
//                }
//            }

            viewModelScope.launch {
                searchingRepository.search(
                    auth = "Bearer BQAEHkNtuH56oVv7oZzupW8182y8QHjt--msHyuqypnUWk778B3q9taGRwCuwWsXZ2CX8uyfWPP2yPsojd3-NKM2GWGKRpPD7eyDXhQrWutHOqpivId_Dw-8fZc67QB3AAE39uTBjkE",
                    searchBody = SearchSpotifyBody(
                        q = "Vua%20Di%20Vua%20Khoc"
                    ),
                ).collect {
                    Log.d("ASDSADASDASDASD", "STATE $it")
                    when (it) {
                        is ResultWrapper.Success -> {
                            spotifyResponse.value =
                                it.value.data().decodeTo(SearchSpotifyResponse::class.java)
                        }

                        else -> {}
                    }
                }
            }
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

    fun searchResultFromSpotify(keyword: String){
        viewModelScope.launch {
            searchingRepository.search(
                auth = "Bearer BQAEHkNtuH56oVv7oZzupW8182y8QHjt--msHyuqypnUWk778B3q9taGRwCuwWsXZ2CX8uyfWPP2yPsojd3-NKM2GWGKRpPD7eyDXhQrWutHOqpivId_Dw-8fZc67QB3AAE39uTBjkE",
                searchBody = SearchSpotifyBody(
                    q = keyword
                ),
            ).collect {
                Log.d("ASDSADASDASDASD", "STATE $it")
                when (it) {
                    is ResultWrapper.Success -> {
                        spotifyResponse.value =
                            it.value.data().decodeTo(SearchSpotifyResponse::class.java)
                    }

                    else -> {}
                }
            }
        }
    }
}