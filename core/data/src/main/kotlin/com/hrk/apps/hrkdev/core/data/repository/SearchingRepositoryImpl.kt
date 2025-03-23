package com.hrk.apps.hrkdev.core.data.repository

import com.hrk.apps.hrkdev.core.network.request.KeyRequest
import com.hrk.apps.hrkdev.core.network.service.RetrofitService
import com.hrk.apps.hrkdev.core.utils.FlowUtils.emitLoading
import com.hrk.apps.hrkdev.core.utils.GoogleMapResponse
import com.hrk.apps.hrkdev.core.utils.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

class SearchingRepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    @Named("io") private val ioDispatcher: CoroutineDispatcher,
) : SearchingRepository {
    override fun youtubeSearching(
        origin: String,
        destination: String,
        key: String
    ): Flow<ResultWrapper<GoogleMapResponse>> {
        return flow {
            val response = retrofitService.getMethodForMap(
                headers = HashMap(),
                request = KeyRequest.SEARCH_PLACE,
                message = "?origin=$origin&destination=$destination&sensor=false&mode=driving&key=$key",
                codeRequired = KeyRequest.SEARCH_PLACE.codeResponse
            )
            emit(response)
        }.emitLoading().flowOn(ioDispatcher)
    }
}