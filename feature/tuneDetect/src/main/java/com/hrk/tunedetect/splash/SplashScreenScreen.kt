package com.hrk.tunedetect.splash

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
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

    val gradientColors = listOf(
        Color(0xFF232526), // dark gray
        Color(0xFF414345), // mid gray
        Color(0xFF6A82FB), // blue accent
        Color(0xFFFC5C7D)  // pink accent
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = gradientColors
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "TuneDetect",
            modifier = Modifier
                .graphicsLayer(alpha = 0.88f)
                .graphicsLayer(translationY = -16f), // lift text up a bit
            style = TextStyle(
                fontSize = 52.sp, // slightly larger
                fontWeight = FontWeight.Bold,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF6A82FB),
                        Color.White,
                        Color(0xFFFC5C7D)
                    ),
                    startX = 0f,
                    endX = 600f,
                    tileMode = TileMode.Clamp
                ),
                shadow = Shadow(
                    color = Color(0xCC6A82FB),
                    offset = Offset(0f, 16f), // more pronounced shadow
                    blurRadius = 48f // more blur for glow
                ),
                textAlign = TextAlign.Center,
                letterSpacing = 2.sp
            )
        )
    }
}
