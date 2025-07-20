package com.hrk.tunedetect.result

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.font.FontWeight.Companion.W900
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrk.apps.hrkdev.core.designsystem.component.DynamicAsyncImage
import com.hrk.apps.hrkdev.core.designsystem.icon.HRKIcons
import com.hrk.apps.hrkdev.core.designsystem.utils.ComposeUtils.clickableSingle
import com.hrk.apps.hrkdev.core.designsystem.utils.shadow
import com.hrk.apps.hrkdev.core.model.iacr_cloud.ACRCloudResponse
import com.hrk.apps.hrkdev.core.model.spotify.TracksItem
import com.hrk.tunedetect.util.orEmpty
import com.hrk.tunedetect.util.textWithStyle

@Composable
fun ResultScreen(
    onBack: () -> Unit,
    acrCloud: ACRCloudResponse,
) {
    val track: TracksItem = TracksItem()

    val trackImage = track.album?.images?.firstOrNull()?.url.orEmpty()
    val releaseDate = track.album?.release_date.orEmpty()
    val artists = track.artists?.firstOrNull()?.name.orEmpty()
    val songName = track.name.orEmpty()
    val duration = convertMillisecondsToMinutes(track.duration_ms)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .clickableSingle {
                            onBack.invoke()
                        }
                        .size(32.dp),
                    painter = painterResource(HRKIcons.BlackIcon.resourceId),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            DynamicAsyncImage(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        alpha = 0.15f
                    },
                imageUrl = trackImage,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DynamicAsyncImage(
                    modifier = Modifier
                        .size(250.dp)
                        .shadow(
                            offsetX = 2.dp,
                            offsetY = 4.dp,
                            color = Color.Black.copy(0.1f),
                            blurRadius = 2.dp
                        ),
                    imageUrl = trackImage,
                    contentDescription = null
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        text = songName,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = W900,
                            color = Color.White
                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DynamicAsyncImage(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(16.dp),
                            imageUrl = trackImage,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "$artists - ".textWithStyle(
                                textSpan = releaseDate,
                                spanStyle = SpanStyle(
                                    fontWeight = W700,
                                    color = Color.White
                                )
                            ).textWithStyle(
                                textSpan = duration,
                                spanStyle = SpanStyle(
                                    fontWeight = W500,
                                    color = Color.White
                                )
                            ),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = W900,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
fun convertMillisecondsToMinutes(ms: Long?): String {
    if (ms == null) return ""
    val minutes = (ms / 1000) / 60
    val seconds = (ms / 1000) % 60
    return String.format("%d:%02d", minutes, seconds)
}