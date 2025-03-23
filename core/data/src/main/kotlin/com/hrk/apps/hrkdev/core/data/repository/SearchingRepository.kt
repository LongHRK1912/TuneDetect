package com.hrk.apps.hrkdev.core.data.repository

import com.hrk.apps.hrkdev.core.utils.GoogleMapResponse
import com.hrk.apps.hrkdev.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface SearchingRepository {
    fun youtubeSearching(
        origin: String,
        destination: String,
        key: String
    ): Flow<ResultWrapper<GoogleMapResponse>>

}