package com.hrk.apps.hrkdev.handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.hrk.apps.hrkdev.core.data.TuneDetectViewModel
import com.hrk.apps.hrkdev.core.model.iacr_cloud.ACRCloudResponse
import com.hrk.apps.hrkdev.core.model.iacr_cloud.IACRCloudState
import com.hrk.apps.hrkdev.ui.bottom_sheet.BottomSheetError

@Composable
fun TuneDetectHandler(
    tuneDetectViewModel: TuneDetectViewModel,
    tuneDetect: IACRCloudState,
    onDismiss: () -> Unit,
    onNavToResultScreen: (ACRCloudResponse?) -> Unit,
    cancelACRCloudClient: () -> Unit,
    startACRCloudClient: () -> Unit,
) {
    LaunchedEffect(tuneDetect) {
        when (tuneDetect) {
            IACRCloudState.Nothing -> {
                cancelACRCloudClient.invoke()
            }

            IACRCloudState.Recording -> {
                startACRCloudClient.invoke()
            }

            is IACRCloudState.Success -> {
                onNavToResultScreen.invoke(tuneDetect.result)
            }

            else -> Unit
        }
    }

    when (tuneDetect) {
        is IACRCloudState.Error -> BottomSheetError(
            tuneDetectViewModel = tuneDetectViewModel,
            tuneDetect = tuneDetect,
            onDismiss = onDismiss
        )

        else -> Unit
    }
}