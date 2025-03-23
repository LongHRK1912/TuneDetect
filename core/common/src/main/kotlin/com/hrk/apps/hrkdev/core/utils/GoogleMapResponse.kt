package com.hrk.apps.hrkdev.core.utils

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import java.util.Objects

open class GoogleMapResponse {
    @SerializedName("results")
    val results: JsonElement? = null

    @SerializedName("geocoded_waypoints")
    val geocoded_waypoints: JsonElement? = null

    @SerializedName("routes")
    val routes: JsonElement? = null

    @SerializedName("status")
    val status: String? = null

    fun data(): String {
        val enCode = JSON.encode(this)
        return Objects.toString(enCode)
    }
}