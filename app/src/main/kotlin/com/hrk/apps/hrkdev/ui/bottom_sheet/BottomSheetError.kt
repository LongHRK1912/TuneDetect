package com.hrk.apps.hrkdev.ui.bottom_sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrk.apps.hrkdev.core.data.TuneDetectViewModel
import com.hrk.apps.hrkdev.core.designsystem.component.bottom_sheet.HRKModalBottomSheet
import com.hrk.apps.hrkdev.core.designsystem.icon.HRKIcons
import com.hrk.apps.hrkdev.core.model.iacr_cloud.IACRCloudState

@Composable
fun BottomSheetError(
    tuneDetectViewModel: TuneDetectViewModel,
    tuneDetect: IACRCloudState,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(tuneDetect is IACRCloudState.Error) {
        HRKModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth(),
            onDismiss = onDismiss,
            hasBottomPadding = true,
            dragHandler = {
                DragItem()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(HRKIcons.InfoIcon.resourceId),
                    contentDescription = null,
                    tint = Color.Red
                )

                Text(
                    text = (tuneDetect as IACRCloudState.Error).message,
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 24.sp,
                        fontWeight = W700
                    ),
                )
            }
        }
    }
}