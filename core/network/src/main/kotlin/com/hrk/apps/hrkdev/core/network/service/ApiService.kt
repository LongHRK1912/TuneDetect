package com.hrk.apps.hrkdev.core.network.service

import com.hrk.apps.hrkdev.core.utils.GoogleMapResponse
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getForMap(
        @HeaderMap headers: Map<String, String>,
        @Url url: String
    ): GoogleMapResponse
}