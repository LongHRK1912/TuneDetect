package com.hrk.apps.hrkdev.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.acrcloud.rec.ACRCloudClient
import com.hrk.apps.hrkdev.R
import com.hrk.apps.hrkdev.core.data.AuthSpotifyUseCase
import com.hrk.apps.hrkdev.core.data.TuneDetectViewModel
import com.hrk.apps.hrkdev.core.data.util.NetworkMonitor
import com.hrk.apps.hrkdev.core.model.iacr_cloud.IACRCloudState
import com.hrk.apps.hrkdev.core.utils.AuthService
import com.hrk.apps.hrkdev.handler.BottomSheetHandler
import com.hrk.apps.hrkdev.navigation.HRKNavHost

@Composable
fun HRKApp(
    networkMonitor: NetworkMonitor,
    tuneDetectViewModel: TuneDetectViewModel,
    acrCloudClient: ACRCloudClient,
) {
    val appState = rememberHRKAppState(
        networkMonitor = networkMonitor,
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    val notConnectedMessage = stringResource(R.string.not_connected)
    val tuneDetect by tuneDetectViewModel.state.collectAsStateWithLifecycle()
    val isServiceInitialized by tuneDetectViewModel.isServiceInitialized.collectAsStateWithLifecycle()

    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = Indefinite,
            )
        }
    }

    LaunchedEffect(tuneDetect) {
        when (tuneDetect) {
            IACRCloudState.Nothing -> cancelACRCloudClient(
                acrCloudClient = acrCloudClient,
            )

            IACRCloudState.Recording -> startACRCloudClient(
                tuneDetectViewModel = tuneDetectViewModel,
                acrCloudClient = acrCloudClient,
                state = tuneDetect,
                isServiceInitialized = isServiceInitialized
            )

            else -> Unit
        }
    }

    BottomSheetHandler(
        tuneDetectViewModel = tuneDetectViewModel,
        tuneDetect = tuneDetect,
        onDismiss = {
            tuneDetectViewModel.resetStateIACRCloud()
        },
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        HRKNavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            appState = appState,
            tuneDetectViewModel = tuneDetectViewModel
        )
    }
}

private fun startACRCloudClient(
    tuneDetectViewModel: TuneDetectViewModel,
    acrCloudClient: ACRCloudClient,
    state: IACRCloudState,
    isServiceInitialized: Boolean
) {
    if (isServiceInitialized.not()) {
        tuneDetectViewModel.updateStateIACRCloud(IACRCloudState.Error("init error"))
        return
    }

    if (state is IACRCloudState.Recording) {
        if (acrCloudClient.startRecognize().not()) {
            tuneDetectViewModel.updateStateIACRCloud(IACRCloudState.Error("start error"))
        }
    }
}

private fun cancelACRCloudClient(
    acrCloudClient: ACRCloudClient,
) {
    acrCloudClient.cancel()
}