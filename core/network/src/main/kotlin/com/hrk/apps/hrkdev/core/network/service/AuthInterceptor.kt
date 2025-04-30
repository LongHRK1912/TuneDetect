package com.hrk.apps.hrkdev.core.network.service

import com.google.gson.GsonBuilder
import com.hrk.apps.hrkdev.core.network.manager.EnvironmentManager
import com.hrk.apps.hrkdev.core.network.request.KeyRequest
import com.hrk.apps.hrkdev.core.utils.AuthService
import com.hrk.apps.hrkdev.core.utils.AuthSpotifyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (AuthService.authSpotify != null) {
            request = request.newBuilder()
                .addHeader(
                    "Authorization",
                    "${AuthService.authSpotify?.token_type} ${AuthService.authSpotify?.access_token}"
                ).build()
        }
        val response = chain.proceed(request)

        if (response.code in 400..500) {
            synchronized(this) {
                response.close()
                return runBlocking(context = Dispatchers.IO) {
                    handleTokenRefresh(chain, request)
                }
            }
        }

        return response
    }

    private suspend fun handleTokenRefresh(
        chain: Interceptor.Chain,
        originalRequest: Request
    ): Response {
        val response = getRefreshToken()

        return if (response.access_token != null) {
            val newRequest = originalRequest.newBuilder()
                .removeHeader("Authorization")
                .addHeader(
                    name = "Authorization",
                    value = "${response.token_type} ${response.access_token}"
                ).build()

            return chain.proceed(newRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }

    private suspend fun getRefreshToken(): AuthSpotifyResponse {
        val gson = GsonBuilder().setLenient().create()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        val retrofit = Retrofit.Builder().client(okHttpClient)
            .baseUrl(EnvironmentManager.currentEnvironment.url)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
        val apiService = retrofit.create(ApiService::class.java)
        return apiService.postBasicAuth(
            auth = AuthService.spotifyTokenAuth(),
            headers = AuthService.headerAuthSpotify(),
            url = KeyRequest.AUTH_SPOTIFY.url,
            encodedData = AuthService.formUrlEncodeAuthSpotify()
        )
    }
}