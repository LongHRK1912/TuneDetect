package com.hrk.apps.hrkdev.ui.bottom_sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrk.apps.hrkdev.core.data.TuneDetectViewModel
import com.hrk.apps.hrkdev.core.designsystem.component.bottom_sheet.HRKModalBottomSheet
import com.hrk.apps.hrkdev.core.designsystem.icon.HRKIcons
import com.hrk.apps.hrkdev.core.model.iacr_cloud.Empty_Result
import com.hrk.apps.hrkdev.core.model.iacr_cloud.IACRCloudState

@Composable
fun BottomSheetResult(
    tuneDetectViewModel: TuneDetectViewModel,
    tuneDetect: IACRCloudState,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(tuneDetect is IACRCloudState.Success) {
        val result = (tuneDetect as IACRCloudState.Success).result
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        HRKModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth(),
            onDismiss = onDismiss,
            hasBottomPadding = true,
            dragHandler = {
                DragItem()
            }
        ) {
            if (result.status?.code == Empty_Result) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Image(
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(HRKIcons.EmptyMusic.resourceId),
                        contentDescription = null,
                    )

                    Text(
                        text = "Sorry, we couldn’t find any songs that match your request.",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontWeight = W700,
                            textAlign = TextAlign.Center
                        ),
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 0.dp, max = screenHeight - 200.dp)
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                }
            }
        }
    }
}