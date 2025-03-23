package com.hrk.apps.hrkdev.core.designsystem.component.bottom_sheet

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HRKModalBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    edgeToEdgeEnabled: Boolean = true,
    hasBottomPadding: Boolean = true,
    skipPartiallyExpanded: Boolean = true,
    dragHandler: @Composable() (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    val scope = rememberCoroutineScope()

    val height = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    LaunchedEffect(Unit) {
        state.expand()
    }

    if (state.isVisible) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = {
                scope.launch {
                    state.hide()
                }.invokeOnCompletion {
                    onDismiss.invoke()
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            dragHandle = dragHandler,
            sheetState = state,
        ) {
            content()
            if (edgeToEdgeEnabled && hasBottomPadding) {
                Spacer(modifier = Modifier.height(height))
            }
        }
    }
}