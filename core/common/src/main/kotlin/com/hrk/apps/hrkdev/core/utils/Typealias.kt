package com.hrk.apps.hrkdev.core.utils

import com.google.gson.JsonElement
import kotlinx.coroutines.flow.Flow

typealias FlowAuthSpotifyResponse = Flow<ResultWrapper<AuthSpotifyResponse>>
typealias FlowSpotifyResponse = Flow<ResultWrapper<SpotifyResponse>>

typealias FlowSpotifyGetTrackDetailResponse = Flow<ResultWrapper<JsonElement>>
