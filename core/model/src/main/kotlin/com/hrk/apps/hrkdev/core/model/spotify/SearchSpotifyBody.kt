package com.hrk.apps.hrkdev.core.model.spotify

data class SearchSpotifyBody(
    val q: String? = null,
    val type: String = "track",
    val limit: Int = 5,
) {
    fun beautyBodyGet(): String {
        val params = listOfNotNull(
            this.q?.let { "q=$it" },
            this.type.let { "type=$it" },
            this.limit.let { "limit=$it" },
        )
        return params.joinToString("&").let { if (it.isNotEmpty()) "?$it" else "" }
    }
}