package com.hrk.apps.hrkdev.core.utils

import java.util.Base64

object AuthService {
    fun spotifyTokenType(): String {
        val clientId = "c9435aacee7b49d8a9a2baa7a7f0eb25"
        val clientSecret = "9e9c2b1e2a6a4a06b50d861f60c13fb0"
        return "Basic " + Base64.getEncoder().encodeToString("$clientId:$clientSecret".toByteArray())
    }

    fun headerContent(): HashMap<String, String> {
        val headers: HashMap<String, String> = HashMap()
        headers["Content-Type"] = "application/x-www-form-urlencoded"
        return headers
    }
}