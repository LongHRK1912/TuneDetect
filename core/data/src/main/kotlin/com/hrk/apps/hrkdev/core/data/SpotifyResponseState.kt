package com.hrk.apps.hrkdev.core.data

import com.hrk.apps.hrkdev.core.model.spotify.SearchSpotifyResponse

sealed class SpotifyResponseState {
    data object Nothing : SpotifyResponseState()
    data class Success(val result: SearchSpotifyResponse) : SpotifyResponseState()
    data class Error(val message: String) : SpotifyResponseState()
    data object Loading : SpotifyResponseState()
}