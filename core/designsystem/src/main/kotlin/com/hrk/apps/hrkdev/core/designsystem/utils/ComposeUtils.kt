package com.hrk.apps.hrkdev.core.designsystem.utils

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import android.os.SystemClock
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

object ComposeUtils {
    @Composable
    inline fun debounced(crossinline onClick: () -> Unit, debounceTime: Long = 1000L): () -> Unit {
        var lastTimeClicked by remember { mutableLongStateOf(0L) }
        val onClickLambda: () -> Unit = {
            val now = SystemClock.uptimeMillis()
            if (now - lastTimeClicked > debounceTime) {
                onClick()
            }
            lastTimeClicked = now
        }
        return onClickLambda
    }

    fun Modifier.debouncedClickable(
        debounceTime: Long = 1000L,
        onClick: () -> Unit
    ): Modifier {
        return this.composed {
            val clickable = debounced(debounceTime = debounceTime, onClick = { onClick() })
            this.clickable { clickable() }
        }
    }

    @SuppressLint("UnnecessaryComposedModifier")
    fun Modifier.backgroundApp() = composed { this.background(color = Color.Black.copy(0.8f)) }

    fun Modifier.shimmerLoading() = composed {
        composed {
            this.background(brush = shimmerBrush())
        }
    }

    @Composable
    fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

    @Composable
    fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

    internal fun interface MultipleEventsCutter {
        fun processEvent(event: () -> Unit)

        companion object
    }

    fun Modifier.clickableSingle(
        enabled: Boolean = true,
        onClickLabel: String? = null,
        role: Role? = null,
        indicated: Boolean = true,
        onClick: () -> Unit
    ) = composed(
        inspectorInfo = debugInspectorInfo {
            name = "clickable"
            properties["enabled"] = enabled
            properties["onClickLabel"] = onClickLabel
            properties["role"] = role
            properties["onClick"] = onClick
        }
    ) {
        val multipleEventsCutter = remember { MultipleEventsCutter.get() }
        this.clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = { multipleEventsCutter.processEvent { onClick() } },
            role = role,
            indication = if (indicated) LocalIndication.current else null,
            interactionSource = remember { MutableInteractionSource() }
        )
    }

    @Composable
    fun keyboardAsState(): State<Boolean> {
        val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
        return rememberUpdatedState(isImeVisible)
    }
}

internal fun ComposeUtils.MultipleEventsCutter.Companion.get(): ComposeUtils.MultipleEventsCutter =
    MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl : ComposeUtils.MultipleEventsCutter {
    val delayTime = 300L
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= delayTime) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}

@SuppressLint("SuspiciousModifierThen")
fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }
            frameworkPaint.color = color.toArgb()

            val leftPixel = offsetX.toPx()
            val topPixel = offsetY.toPx()
            val rightPixel = size.width + topPixel
            val bottomPixel = size.height + leftPixel

            canvas.drawRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                paint = paint
            )
        }
    }
)

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f)
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800),
                repeatMode = RepeatMode.Reverse
            ),
            label = ""
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}

fun <T> NavHostController.setData(
    key: String,
    value: T?
) {
    currentBackStackEntry?.savedStateHandle?.set(key, value)
}

fun <T> NavHostController.removeKey(
    key: String
) {
    currentBackStackEntry?.savedStateHandle?.remove<T>(key)
}

fun <T> NavHostController.removePrevKey(
    key: String
) {
    previousBackStackEntry?.savedStateHandle?.remove<T>(key)
}

fun <T> NavHostController.setPrevData(
    key: String,
    value: T?
) {
    previousBackStackEntry
        ?.savedStateHandle
        ?.set(key, value)
}

fun <T> NavHostController.getPrevData(key: String): T? {
    return previousBackStackEntry?.savedStateHandle?.get<T>(
        key
    )
}

fun <T> NavHostController.getData(key: String): T? {
    return currentBackStackEntry?.savedStateHandle?.get<T>(
        key
    )
}