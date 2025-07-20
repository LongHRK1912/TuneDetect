package com.hrk.apps.hrkdev.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.acrcloud.rec.ACRCloudClient
import com.hrk.apps.hrkdev.R
import com.hrk.apps.hrkdev.core.NavigationKey.ACRCloudResponseKey
import com.hrk.apps.hrkdev.core.data.TuneDetectViewModel
import com.hrk.apps.hrkdev.core.data.util.NetworkMonitor
import com.hrk.apps.hrkdev.core.designsystem.utils.setData
import com.hrk.apps.hrkdev.core.model.iacr_cloud.IACRCloudState
import com.hrk.apps.hrkdev.core.utils.JSON.toJson
import com.hrk.apps.hrkdev.handler.TuneDetectHandler
import com.hrk.apps.hrkdev.navigation.HRKNavHost
import com.hrk.tunedetect.result.navigation.navigateToResultScreen

@Composable
fun HRKApp(
    networkMonitor: NetworkMonitor,
    tuneDetectViewModel: TuneDetectViewModel,
    acrCloudClient: ACRCloudClient,
) {
    val appState = rememberHRKAppState(
        networkMonitor = networkMonitor,
    )

    val navController = appState.navController

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

    TuneDetectHandler(
        tuneDetectViewModel = tuneDetectViewModel,
        tuneDetect = tuneDetect,
        onDismiss = {
            tuneDetectViewModel.resetStateIACRCloud()
        },
        cancelACRCloudClient = {
            cancelACRCloudClient(acrCloudClient = acrCloudClient)
        },
        startACRCloudClient = {
            startACRCloudClient(
                tuneDetectViewModel = tuneDetectViewModel,
                acrCloudClient = acrCloudClient,
                state = tuneDetect,
                isServiceInitialized = isServiceInitialized
            )
        },
        onNavToResultScreen = {
            navController.setData(ACRCloudResponseKey, it.toJson())
            navController.navigateToResultScreen()
            tuneDetectViewModel.resetStateIACRCloud()
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        HRKNavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            tuneDetectViewModel = tuneDetectViewModel,
            navController = navController
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