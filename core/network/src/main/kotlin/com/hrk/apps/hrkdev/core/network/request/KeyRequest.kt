package com.hrk.apps.hrkdev.core.network.request

enum class KeyRequest(val url: String, val codeResponse: String = "") {
    //MAP
    AUTH_SPOTIFY("https://accounts.spotify.com/api/token"),
    SEARCH_SPOTIFY("https://api.spotify.com/v1/search"),
    ;
}