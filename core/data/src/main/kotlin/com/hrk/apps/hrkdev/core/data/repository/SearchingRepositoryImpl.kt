package com.hrk.apps.hrkdev.core.data.repository

import com.hrk.apps.hrkdev.core.model.spotify.SearchSpotifyBody
import com.hrk.apps.hrkdev.core.network.Dispatcher
import com.hrk.apps.hrkdev.core.network.HRKDispatchers.IO
import com.hrk.apps.hrkdev.core.network.request.KeyRequest
import com.hrk.apps.hrkdev.core.network.service.RetrofitService
import com.hrk.apps.hrkdev.core.utils.FlowAuthSpotifyResponse
import com.hrk.apps.hrkdev.core.utils.FlowSpotifyResponse
import com.hrk.apps.hrkdev.core.utils.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SearchingRepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : SearchingRepository {
    override fun auth(): FlowAuthSpotifyResponse {
        val authHeader =
            "Basic Yzk0MzVhYWNlZTdiNDlkOGE5YTJiYWE3YTdmMGViMjU6OWU5YzJiMWUyYTZhNGEwNmI1MGQ4NjFmNjBjMTNmYjA="
        val headers = mapOf(
            "Content-Type" to "application/x-www-form-urlencoded",
            "Cookie" to "__Host-device_id=AQCMEuUEWnilqHfgErDCTbTSUntLPVMRM8fyQeVrpeGOp_VyAM1INwhop96wYOewGOpdWacKzaZtPJLbJYe5MP7D1Ll2XplyJDQ; sp_tr=false"
        )
        val bodyData = mapOf("grant_type" to "client_credentials")
        return flow {
            val response = retrofitService.authMethod(
                auth = authHeader,
                headers = headers,
                request = KeyRequest.AUTH_SPOTIFY,
                codeRequired = KeyRequest.AUTH_SPOTIFY.codeResponse,
                formUrlEncoded = bodyData
            )
            emit(response)
        }.onStart { emit(ResultWrapper.Loading) }.flowOn(ioDispatcher)
    }

    override fun search(auth: String, searchBody: SearchSpotifyBody): FlowSpotifyResponse {
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
}