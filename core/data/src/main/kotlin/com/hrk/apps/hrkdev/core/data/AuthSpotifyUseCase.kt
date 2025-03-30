package com.hrk.apps.hrkdev.core.data

import com.hrk.apps.hrkdev.core.network.request.KeyRequest
import com.hrk.apps.hrkdev.core.network.service.RetrofitService
import com.hrk.apps.hrkdev.core.utils.AuthService
import com.hrk.apps.hrkdev.core.utils.JSON.toJson
import com.hrk.preferences.Preferences
import javax.inject.Inject

class AuthSpotifyUseCase @Inject constructor(
    private val retrofitService: RetrofitService,
    private val preferences: Preferences
) {
    suspend operator fun invoke() {
        if (AuthService.authSpotify == null) {
            val response = retrofitService.authMethod(
                auth = AuthService.spotifyTokenAuth(),
                headers = AuthService.headerAuthSpotify(),
                request = KeyRequest.AUTH_SPOTIFY,
                codeRequired = KeyRequest.AUTH_SPOTIFY.codeResponse,
                formUrlEncoded = AuthService.formUrlEncodeAuthSpotify()
            )
            AuthService.retrieveAuthSpotifyResponse(response)?.let {
                preferences.spotifyToken = it.toJson()
            }
        }
    }
}