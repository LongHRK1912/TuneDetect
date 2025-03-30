package com.hrk.apps.hrkdev.core.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrk.apps.hrkdev.core.data.repository.SearchingRepository
import com.hrk.apps.hrkdev.core.model.iacr_cloud.IACRCloudState
import com.hrk.apps.hrkdev.core.model.spotify.SearchSpotifyBody
import com.hrk.apps.hrkdev.core.model.spotify.SearchSpotifyResponse
import com.hrk.apps.hrkdev.core.utils.AuthService
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

    private var _spotifyResponse =
        MutableStateFlow<SpotifyResponseState>(SpotifyResponseState.Nothing)
    val spotifyResponse = _spotifyResponse.asStateFlow()

    fun updateServiceInitialized(isInitialized: Boolean) {
        _isServiceInitialized.value = isInitialized
    }

    private val _volume = MutableStateFlow(0.0)
    val volume = _volume.asStateFlow()

    fun onVolumeChanged(volume: Double) {
        _volume.value = volume
    }

    private val _state = MutableStateFlow<IACRCloudState>(IACRCloudState.Nothing)
    val state = _state.asStateFlow()

    fun updateStateIACRCloud(newState: IACRCloudState) {
        when (newState) {
            is IACRCloudState.Success -> {
                if (newState.result.metadata?.music.orEmpty().isNotEmpty()) {
                    val musics = newState.result.metadata?.music.orEmpty()

                    val nameArtists = musics.map { artists ->
                        artists.artists?.map {
                            it.name
                        }?.joinToString(" ")
                    }

                    val nameAlbum = musics.map { album ->
                        album.album?.name
                    }

                    val nameSong = musics.map { album ->
                        album.title
                    }


                    val keyword = nameAlbum.mapIndexed { index, album ->
                        nameSong + " " + album + " " + nameArtists[index]
                    }.joinToString(", ")

                    searchResultFromSpotify(keyword)
                } else {
                    _spotifyResponse.value = SpotifyResponseState.Success(
                        SearchSpotifyResponse()
                    )
                }
            }

            else -> Unit
        }

        _state.value = newState
    }

    fun resetStateIACRCloud() {
        _state.value = IACRCloudState.Nothing
        _spotifyResponse.value = SpotifyResponseState.Nothing
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

    private fun searchResultFromSpotify(keyword: String) {
        viewModelScope.launch {
            AuthService.authSpotify?.let {
                Log.d("SDFDSFSDFSDFSDF", "searchResultFromSpotify: $it")
                Log.d("SDFDSFSDFSDFSDF", "getToken: ${it.getToken()}")
                searchingRepository.search(
                    auth = it.getToken(),
                    searchBody = SearchSpotifyBody(
                        q = keyword
                    ),
                ).collect { response ->
                    when (response) {
                        is ResultWrapper.Success -> {
                            _spotifyResponse.value = SpotifyResponseState.Success(
                                response.value.data().decodeTo(SearchSpotifyResponse::class.java)
                                    ?: SearchSpotifyResponse()
                            )
                        }

                        is ResultWrapper.Error -> {
                            _spotifyResponse.value = SpotifyResponseState.Error(
                                message = response.message.orEmpty()
                            )
                        }

                        ResultWrapper.Loading -> {
                            _spotifyResponse.value = SpotifyResponseState.Loading
                        }
                    }
                }
            }
        }
    }
}