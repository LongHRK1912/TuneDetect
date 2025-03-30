package com.hrk.apps.hrkdev.handler

import androidx.compose.runtime.Composable
import com.hrk.apps.hrkdev.core.data.TuneDetectViewModel
import com.hrk.apps.hrkdev.core.model.iacr_cloud.IACRCloudState
import com.hrk.apps.hrkdev.ui.bottom_sheet.BottomSheetError
import com.hrk.apps.hrkdev.ui.bottom_sheet.BottomSheetResult

@Composable
fun BottomSheetHandler(
    tuneDetectViewModel: TuneDetectViewModel,
    tuneDetect: IACRCloudState,
    onDismiss: () -> Unit
) {
    when (tuneDetect) {
        is IACRCloudState.Error -> BottomSheetError(
            tuneDetectViewModel = tuneDetectViewModel,
            tuneDetect = tuneDetect,
            onDismiss = onDismiss
        )

        is IACRCloudState.Success -> BottomSheetResult(
            tuneDetectViewModel = tuneDetectViewModel,
            onDismiss = onDismiss
        )

        else -> Unit
    }
}