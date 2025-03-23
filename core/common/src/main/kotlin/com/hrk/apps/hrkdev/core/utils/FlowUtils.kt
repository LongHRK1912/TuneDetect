package com.hrk.apps.hrkdev.core.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

object FlowUtils {
    fun Flow<ResultWrapper<GoogleMapResponse>>.emitLoading(): Flow<ResultWrapper<GoogleMapResponse>> {
        return onStart { emit(ResultWrapper.Loading) }
    }
}