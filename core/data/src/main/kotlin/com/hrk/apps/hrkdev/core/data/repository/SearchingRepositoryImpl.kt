package com.hrk.apps.hrkdev.core.data.repository

import com.hrk.apps.hrkdev.core.model.spotify.SearchSpotifyBody
import com.hrk.apps.hrkdev.core.network.Dispatcher
import com.hrk.apps.hrkdev.core.network.HRKDispatchers.IO
import com.hrk.apps.hrkdev.core.network.request.KeyRequest
import com.hrk.apps.hrkdev.core.network.service.RetrofitService
import com.hrk.apps.hrkdev.core.utils.AuthService
import com.hrk.apps.hrkdev.core.utils.FlowAuthSpotifyResponse
import com.hrk.apps.hrkdev.core.utils.FlowSpotifyGetTrackDetailResponse
import com.hrk.apps.hrkdev.core.utils.FlowSpotifyResponse
import com.hrk.apps.hrkdev.core.utils.JSON.toJson
import com.hrk.apps.hrkdev.core.utils.ResultWrapper
import com.hrk.preferences.Preferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SearchingRepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val preferences: Preferences
) : SearchingRepository {
    override fun auth(): FlowAuthSpotifyResponse {
        return flow {
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
            emit(response)
        }.onStart { emit(ResultWrapper.Loading) }.flowOn(ioDispatcher)
    }

    override fun search(
        auth: String,
        searchBody: SearchSpotifyBody
    ): FlowSpotifyResponse {
        return flow {
            val response = retrofitService.searchTrackMethod(
                auth = auth,
                request = KeyRequest.SEARCH_SPOTIFY,
                codeRequired = KeyRequest.SEARCH_SPOTIFY.codeResponse,
                message = searchBody.beautyBodyGet()
            )
            emit(response)
        }.onStart { emit(ResultWrapper.Loading) }.flowOn(ioDispatcher)
    }

    override fun trackDetail(
        auth: String,
        trackId: String
    ): FlowSpotifyGetTrackDetailResponse {
        return flow {
            val response = retrofitService.getTrackDetailMethod(
                auth = auth,
                request = KeyRequest.GET_TRACK_SPOTIFY,
                codeRequired = KeyRequest.GET_TRACK_SPOTIFY.codeResponse,
                message = trackId
            )
            emit(response)
        }.onStart { emit(ResultWrapper.Loading) }.flowOn(ioDispatcher)
    }
}