package com.hrk.apps.hrkdev.core.network.service

import com.hrk.apps.hrkdev.core.utils.AuthSpotifyResponse
import com.hrk.apps.hrkdev.core.utils.SpotifyResponse
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    @FormUrlEncoded
    @POST
    suspend fun postBasicAuth(
        @Header("Authorization") auth: String,
        @HeaderMap headers: Map<String, String>,
        @Url url: String,
        @FieldMap encodedData: Map<String, String>
    ): AuthSpotifyResponse

    @GET
    suspend fun searchTrack(
        @Header("Authorization") auth: String,
        @Url url: String,
    ): SpotifyResponse
}