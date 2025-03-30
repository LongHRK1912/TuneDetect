package com.hrk.apps.hrkdev.core

import com.hrk.apps.hrkdev.core.network.Dispatcher
import com.hrk.apps.hrkdev.core.network.HRKDispatchers.IO
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SpotifyAuthManager @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) {
}