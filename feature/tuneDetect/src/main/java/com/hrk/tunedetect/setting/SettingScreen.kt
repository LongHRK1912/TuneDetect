package com.hrk.tunedetect.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun SettingScreen(
    onNextScreen: () -> Unit,
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNextScreen.invoke()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    )
}