package com.hrk.apps.hrkdev.core.utils

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import java.util.Objects

open class SpotifyResponse {
    @SerializedName("tracks")
    val data: JsonElement? = null

    @SerializedName("error")
    val error: JsonElement? = null

    fun data(): String {
        val enCode = JSON.encode(this)
        return Objects.toString(enCode)
    }
}