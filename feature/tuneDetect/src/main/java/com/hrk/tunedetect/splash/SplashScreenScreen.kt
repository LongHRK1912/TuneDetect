package com.hrk.tunedetect.splash

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.hrk.apps.hrkdev.core.designsystem.icon.HRKIcons
import kotlinx.coroutines.delay

@OptIn(UnstableApi::class)
@Composable
fun SplashScreenScreen(
    onFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1500)
        onFinished.invoke()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(CircleShape)
                .size(48.dp),
            painter = painterResource(HRKIcons.Logo.resourceId),
            contentDescription = null
        )
    }
}
