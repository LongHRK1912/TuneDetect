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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.font.FontWeight.Companion.W900
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hrk.apps.hrkdev.core.designsystem.component.DynamicAsyncImage
import com.hrk.apps.hrkdev.core.designsystem.icon.HRKIcons
import com.hrk.apps.hrkdev.core.designsystem.utils.ComposeUtils.clickableSingle
import com.hrk.apps.hrkdev.core.designsystem.utils.ComposeUtils.shimmerLoading
import com.hrk.apps.hrkdev.core.designsystem.utils.shadow
import com.hrk.apps.hrkdev.core.model.iacr_cloud.ACRCloudResponse
import com.hrk.tunedetect.util.orEmpty
import com.hrk.tunedetect.util.textWithStyle

@Composable
fun ResultScreen(
    onBack: () -> Unit,
    acrCloud: ACRCloudResponse?,
    viewmodel: ResultViewModel = hiltViewModel<ResultViewModel, ResultViewModel.Factory>(
        creationCallback = { factory -> factory.create(acrCloud = acrCloud) },
    )
) {
    val spotifyState by viewmodel.spotifyState.collectAsState()

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
        when (spotifyState) {
            is SpotifyTrackState.ERROR -> TODO()
            SpotifyTrackState.LOADING -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(250.dp)
                            .shimmerLoading()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .padding(horizontal = 32.dp)
                                .shimmerLoading(),
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp)
                                .padding(horizontal = 16.dp)
                                .shimmerLoading(),
                        )
                    }
                }
            }

            is SpotifyTrackState.SUCCESS -> {
                val trackDetail = (spotifyState as SpotifyTrackState.SUCCESS).track

                val trackImage = trackDetail.album?.images?.firstOrNull()?.url.orEmpty()
                val songName = trackDetail.name.orEmpty()
                val duration = convertMillisecondsToMinutes(trackDetail.duration_ms)
                val releaseDate = trackDetail.album?.release_date.orEmpty()
                val artists = trackDetail.artists?.firstOrNull()?.name.orEmpty()

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
                        Box(
                            modifier = Modifier.size(250.dp)
                        ) {
                            DynamicAsyncImage(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .shadow(
                                        offsetX = 2.dp,
                                        offsetY = 4.dp,
                                        color = Color.Black.copy(0.1f),
                                        blurRadius = 2.dp
                                    ),
                                imageUrl = trackImage,
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier
                                    .padding(
                                        bottom = 12.dp,
                                        end = 12.dp
                                    )
                                    .align(Alignment.BottomEnd),
                                text = duration,
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = W900,
                                    color = Color.White
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

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

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp
                                    ),
                                text = "$artists\n".textWithStyle(
                                    textSpan = "$releaseDate ",
                                    spanStyle = SpanStyle(
                                        fontWeight = W700,
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

            else -> Unit
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