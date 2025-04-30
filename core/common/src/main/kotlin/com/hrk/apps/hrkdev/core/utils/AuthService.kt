package com.hrk.apps.hrkdev.core.utils

import java.util.Base64


object AuthService {

    var authSpotify: AuthSpotifyResponse? = null

    fun retrieveAuthSpotifyResponse(response: ResultWrapper<AuthSpotifyResponse>): AuthSpotifyResponse? {
        if (response is ResultWrapper.Success) {
            response.value.let { authResponse ->
                authSpotify = authResponse
                return authResponse
            }
        }

        return null
    }

    fun headerAuthSpotify() = mapOf(
        "Content-Type" to "application/x-www-form-urlencoded",
        "Cookie" to "__Host-device_id=AQCMEuUEWnilqHfgErDCTbTSUntLPVMRM8fyQeVrpeGOp_VyAM1INwhop96wYOewGOpdWacKzaZtPJLbJYe5MP7D1Ll2XplyJDQ; sp_tr=false"
    )

    fun spotifyTokenAuth(): String {
        val clientId = "c9435aacee7b49d8a9a2baa7a7f0eb25"
        val clientSecret = "9e9c2b1e2a6a4a06b50d861f60c13fb0"
        return "Basic " + Base64.getEncoder()
            .encodeToString("$clientId:$clientSecret".toByteArray())
    }

    fun formUrlEncodeAuthSpotify() = mapOf("grant_type" to "client_credentials")
}