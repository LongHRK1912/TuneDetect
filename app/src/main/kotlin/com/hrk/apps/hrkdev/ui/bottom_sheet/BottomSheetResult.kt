package com.hrk.apps.hrkdev.ui.bottom_sheet

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.hrk.apps.hrkdev.core.data.SpotifyResponseState
import com.hrk.apps.hrkdev.core.data.TuneDetectViewModel
import com.hrk.apps.hrkdev.core.designsystem.component.DynamicAsyncImage
import com.hrk.apps.hrkdev.core.designsystem.component.bottom_sheet.HRKModalBottomSheet
import com.hrk.apps.hrkdev.core.designsystem.icon.HRKIcons
import com.hrk.apps.hrkdev.core.designsystem.utils.ComposeUtils.shimmerLoading
import com.hrk.apps.hrkdev.core.designsystem.utils.shadow
import com.hrk.apps.hrkdev.core.model.spotify.SearchSpotifyResponse
import com.hrk.apps.hrkdev.core.model.spotify.TracksItem
import com.hrk.tunedetect.util.orEmpty
import com.hrk.tunedetect.util.textWithStyle

@Composable
fun BottomSheetResult(
    tuneDetectViewModel: TuneDetectViewModel,
    onDismiss: () -> Unit
) {
    val spotifyResponseState by tuneDetectViewModel.spotifyResponse.collectAsState()

    AnimatedVisibility(spotifyResponseState !is SpotifyResponseState.Nothing) {
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
            when (spotifyResponseState) {
                is SpotifyResponseState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 0.dp, max = screenHeight - 200.dp)
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
                            text = (spotifyResponseState as SpotifyResponseState.Error).message,
                            style = TextStyle(
                                color = Color.Red,
                                fontSize = 24.sp,
                                fontWeight = W700
                            ),
                        )
                    }
                }

                SpotifyResponseState.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 0.dp, max = screenHeight - 200.dp)
                            .padding(horizontal = 16.dp, vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        SpotifyResultComponentShimmer(
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }

                is SpotifyResponseState.Success -> {
                    val spotifyResponse =
                        (spotifyResponseState as SpotifyResponseState.Success).result

                    if (spotifyResponse.tracks?.items.isNullOrEmpty()) {
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
                            SpotifyResultComponent(
                                modifier = Modifier.fillMaxWidth(),
                                spotifyResponse = spotifyResponse
                            )
                        }
                    }
                }

                else -> Unit
            }

        }
    }
}

@Composable
fun SpotifyResultComponentShimmer(
    modifier: Modifier,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Spotify",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF1DB954),
                            Color(0xFF1ED760)
                        )
                    ),
                    fontWeight = W900,
                    fontSize = 32.sp
                )
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(5) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .fillMaxWidth()
                            .aspectRatio(2f)
                            .shimmerLoading()
                    )
                }
            }
        }
    }
}

@Composable
fun SpotifyResultComponent(
    modifier: Modifier,
    spotifyResponse: SearchSpotifyResponse?
) {
    spotifyResponse?.let { spotify ->
        val tracks = spotify.tracks?.items.orEmpty()

        Box(modifier = modifier) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Spotify",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF1DB954),
                                Color(0xFF1ED760)
                            )
                        ),
                        fontWeight = W900,
                        fontSize = 32.sp
                    )
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(tracks) { track ->
                        SpotifyItemComponent(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .fillMaxWidth(),
                            track = track
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SpotifyItemComponent(
    modifier: Modifier = Modifier,
    track: TracksItem
) {
    val trackImage = track.album?.images?.firstOrNull()?.url.orEmpty()
    val releaseDate = track.album?.release_date.orEmpty()
    val artists = track.artists?.firstOrNull()?.name.orEmpty()
    val songName = track.name.orEmpty()
    val duration = convertMillisecondsToMinutes(track.duration_ms)

    Box(
        modifier = modifier
            .fillMaxWidth()
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
            contentScale = ContentScale.FillWidth
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier.size(100.dp)) {
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
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 5.dp),
                    text = artists,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = W500,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Song",
                    style = TextStyle(
                        fontSize = 8.sp,
                        fontWeight = W500,
                        color = Color.White
                    )
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = songName,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = W900,
                        color = Color.White
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DynamicAsyncImage(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(10.dp),
                        imageUrl = trackImage,
                        contentDescription = null
                    )

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
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontWeight = W900,
                            color = Color.White
                        )
                    )
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