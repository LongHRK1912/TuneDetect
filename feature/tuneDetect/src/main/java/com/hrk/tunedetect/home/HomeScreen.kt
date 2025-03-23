package com.hrk.tunedetect.home

import android.Manifest.permission.INTERNET
import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.hrk.apps.hrkdev.core.data.TuneDetectViewModel
import com.hrk.apps.hrkdev.core.designsystem.icon.HRKIcons
import com.hrk.apps.hrkdev.core.model.iacr_cloud.IACRCloudState

@Composable
fun HomeScreen(
    tuneDetectViewModel: TuneDetectViewModel,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val tuneState by tuneDetectViewModel.state.collectAsState()
    val volume by tuneDetectViewModel.volume.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        RequestPermissionWithoutAccompanist()
        AppBackground()
        GlowingNeonButton(
            modifier = Modifier
                .align(Alignment.Center)
                .size(150.dp),
            tuneState = tuneState,
            onChangeState = {
                tuneDetectViewModel.handlerClicked()
            }
        )
    }
}

@Composable
fun RequestPermissionWithoutAccompanist() {
    val context = LocalContext.current
    val permissions = arrayOf(
        INTERNET,
        RECORD_AUDIO
    )

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsResult ->
        val granted = permissionsResult.entries.all { it.value }
        Toast.makeText(
            context,
            if (granted) "All Permissions Granted ✅" else "Some Permissions Denied ❌",
            Toast.LENGTH_SHORT
        ).show()
    }

    val allPermissionsGranted = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    LaunchedEffect(Unit) {
        if (!allPermissionsGranted) {
            requestPermissionLauncher.launch(permissions)
        }
    }
}

@kotlin.OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GlowingNeonButton(
    modifier: Modifier,
    tuneState: IACRCloudState,
    onChangeState: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    var isStart by remember(tuneState) {
        mutableStateOf(tuneState is IACRCloudState.Nothing)
    }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "scale"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isStart) Color.Cyan else Color.Red,
        animationSpec = tween(500),
        label = "borderColor"
    )

    val startAlpha by animateFloatAsState(
        targetValue = if (isStart) 1f else 0f,
        animationSpec = tween(300),
        label = "startAlpha"
    )
    val stopAlpha by animateFloatAsState(
        targetValue = if (isStart) 0f else 1f,
        animationSpec = tween(300),
        label = "stopAlpha"
    )

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .scale(scale)
                .pointerInteropFilter { motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> isPressed = true
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            isPressed = false
                            onChangeState.invoke()
                        }
                    }
                    true
                },
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.minDimension / 2

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(borderColor.copy(alpha = 0.6f), Color.Transparent),
                        center = center,
                        radius = radius * 1.3f
                    ),
                    radius = radius * 1.3f,
                    center = center
                )

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(borderColor.copy(alpha = 0.8f), Color.Transparent),
                        center = center,
                        radius = radius * 1.1f
                    ),
                    radius = radius * 1.1f,
                    center = center
                )

                drawCircle(
                    color = Color.Black,
                    radius = radius * 0.85f,
                    center = center
                )

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = 0.2f), Color.Transparent),
                        center = center,
                        radius = radius * 0.6f
                    ),
                    radius = radius * 0.6f,
                    center = center
                )
            }

            Text(
                text = "START",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = startAlpha)
            )

            Text(
                text = "STOP",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = stopAlpha)
            )
        }
    }
}

@Composable
fun AppBackground() {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = HRKIcons.BackgroundApp.resourceId),
        contentDescription = "App Background",
        contentScale = ContentScale.Crop
    )
}