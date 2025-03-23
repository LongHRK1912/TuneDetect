package com.hrk.apps.hrkdev.core.network.request

enum class KeyRequest(val url: String, val codeResponse: String,) {
    //MAP
    SEARCH_PLACE("https://maps.googleapis.com/maps/api/place/autocomplete/json?","200"),
    ;
}