package com.hrk.apps.hrkdev.core.network.service

import android.util.Log
import com.google.gson.JsonElement
import com.hrk.apps.hrkdev.core.network.request.KeyRequest
import com.hrk.apps.hrkdev.core.utils.AuthSpotifyResponse
import com.hrk.apps.hrkdev.core.utils.NetworkConstant
import com.hrk.apps.hrkdev.core.utils.ResultWrapper
import com.hrk.apps.hrkdev.core.utils.SpotifyResponse
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RetrofitService @Inject constructor(
    private val apiService: ApiService,
) {
    companion object {
        const val TIME_OUT = 30000L
        const val LONG_TIME_OUT = 120000L
    }

    suspend fun authMethod(
        auth: String,
        headers: Map<String, String>,
        request: KeyRequest,
        formUrlEncoded: Map<String, String>,
        codeRequired: String
    ): ResultWrapper<AuthSpotifyResponse> {
        return authSafeApiCall({
            apiService.postBasicAuth(auth, headers, request.url, formUrlEncoded)
        }, codeRequired)
    }

    suspend fun searchTrackMethod(
        auth: String,
        request: KeyRequest,
        message: String,
        codeRequired: String
    ): ResultWrapper<SpotifyResponse> {
        return searchSpotifySafeApiCall({
            apiService.searchTrack(
                auth, request.url + message
            )
        }, codeRequired)
    }

    suspend fun getTrackDetailMethod(
        auth: String,
        request: KeyRequest,
        message: String,
        codeRequired: String
    ): ResultWrapper<JsonElement> {
        return trackDetailSpotifySafeApiCall({
            apiService.getTrack(
                auth, request.url + message
            )
        }, codeRequired)
    }

    private suspend fun authSafeApiCall(
        apiCall: suspend () -> AuthSpotifyResponse,
        codeRequired: String
    ): ResultWrapper<AuthSpotifyResponse> =
        withTimeoutOrNull(TIME_OUT) {
            try {
                val response = apiCall.invoke()
                ResultWrapper.Success(response)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> {
                        ResultWrapper.Error(NetworkConstant.IOException, throwable.message)
                    }

                    is HttpException -> {
                        val code = throwable.code()
                        ResultWrapper.Error(code.toString(), throwable.message)
                    }

                    is TimeoutCancellationException -> {
                        ResultWrapper.Error(
                            NetworkConstant.TimeoutCancellationException,
                            throwable.message
                        )
                    }

                    else -> {
                        ResultWrapper.Error(NetworkConstant.UnknownError, throwable.message)
                    }
                }
            }
        } ?: ResultWrapper.Error(
            code = NetworkConstant.TimeOutCodeResponse,
            message = NetworkConstant.TimeOut,
            data = NetworkConstant.TimeOut
        )

    private suspend fun searchSpotifySafeApiCall(
        apiCall: suspend () -> SpotifyResponse,
        codeRequired: String
    ): ResultWrapper<SpotifyResponse> =
        withTimeoutOrNull(TIME_OUT) {
            try {
                val response = apiCall.invoke()
                val data = response.data()
                if (data != null) {
                    ResultWrapper.Success(response)
                } else {
                    ResultWrapper.Error(message = response.data())
                }
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> {
                        ResultWrapper.Error(NetworkConstant.IOException, throwable.message)
                    }

                    is HttpException -> {
                        val code = throwable.code()
                        ResultWrapper.Error(code.toString(), throwable.message)
                    }

                    is TimeoutCancellationException -> {
                        ResultWrapper.Error(
                            NetworkConstant.TimeoutCancellationException,
                            throwable.message
                        )
                    }

                    else -> {
                        ResultWrapper.Error(NetworkConstant.UnknownError, throwable.message)
                    }
                }
            }
        } ?: ResultWrapper.Error(
            code = NetworkConstant.TimeOutCodeResponse,
            message = NetworkConstant.TimeOut,
            data = NetworkConstant.TimeOut
        )

    private suspend fun trackDetailSpotifySafeApiCall(
        apiCall: suspend () -> JsonElement,
        codeRequired: String
    ): ResultWrapper<JsonElement> =
        withTimeoutOrNull(TIME_OUT) {
            try {
                val response = apiCall.invoke()
                if (response != null) {
                    ResultWrapper.Success(response)
                } else {
                    ResultWrapper.Error(message = response)
                }
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> {
                        ResultWrapper.Error(NetworkConstant.IOException, throwable.message)
                    }

                    is HttpException -> {
                        val code = throwable.code()
                        ResultWrapper.Error(code.toString(), throwable.message)
                    }

                    is TimeoutCancellationException -> {
                        ResultWrapper.Error(
                            NetworkConstant.TimeoutCancellationException,
                            throwable.message
                        )
                    }

                    else -> {
                        ResultWrapper.Error(NetworkConstant.UnknownError, throwable.message)
                    }
                }
            }
        } ?: ResultWrapper.Error(
            code = NetworkConstant.TimeOutCodeResponse,
            message = NetworkConstant.TimeOut,
            data = NetworkConstant.TimeOut
        )
}