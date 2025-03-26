package com.hrk.apps.hrkdev.core.data.repository

import com.hrk.apps.hrkdev.core.model.spotify.SearchSpotifyBody
import com.hrk.apps.hrkdev.core.utils.FlowAuthSpotifyResponse
import com.hrk.apps.hrkdev.core.utils.FlowSpotifyResponse

interface SearchingRepository {
    fun auth(): FlowAuthSpotifyResponse
    fun search(auth: String, searchBody: SearchSpotifyBody): FlowSpotifyResponse
}