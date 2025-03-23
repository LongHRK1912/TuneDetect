package com.hrk.apps.hrkdev.core.network.service

import com.hrk.apps.hrkdev.core.network.di.NetworkModule
import com.hrk.apps.hrkdev.core.network.request.KeyRequest
import com.hrk.apps.hrkdev.core.utils.GoogleMapResponse
import com.hrk.apps.hrkdev.core.utils.NetworkConstant
import com.hrk.apps.hrkdev.core.utils.ResultWrapper
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RetrofitService @Inject constructor(
    @NetworkModule.MainInterceptor private val apiService: ApiService,
) {
    companion object {
        const val TIME_OUT = 30000L
        const val LONG_TIME_OUT = 120000L
    }

    suspend fun getMethodForMap(
        headers: Map<String, String>,
        request: KeyRequest,
        message: Any? = null,
        codeRequired: String
    ): ResultWrapper<GoogleMapResponse> {
        return safeApiCallForMap({
            apiService.getForMap(headers, request.url + message)
        }, codeRequired)
    }

    private suspend fun safeApiCallForMap(
        apiCall: suspend () -> GoogleMapResponse,
        codeRequired: String
    ): ResultWrapper<GoogleMapResponse> =
        withTimeoutOrNull(TIME_OUT) {
            try {
                val response = apiCall.invoke()
                if (response.status == "OK") {
                    ResultWrapper.Success(response)
                } else {
                    ResultWrapper.Error(
                        message = response.status
                    )
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