package com.example.tiktokcompose.ui.composables

import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.media3.common.Player
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter
import com.example.tiktokcompose.ui.state.VideoUiState
import com.example.tiktokcompose.viewmodel.TikTokViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@Composable
fun TikTokScreen(
    modifier: Modifier = Modifier,
    viewModel: TikTokViewModel = hiltViewModel()
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val state by viewModel.state.collectAsState()
        VideoPager(
            state = state
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun VideoPager(
    state: VideoUiState,
    viewModel: TikTokViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.distinctUntilChanged().collect { page ->
            pagerState.animateScrollToPage(page)
        }
    }

    VerticalPager(
        count = state.videos.size,
        state = pagerState,
        horizontalAlignment = Alignment.CenterHorizontally,
        key = {
            state.videos[it].id
        }
    ) { index ->
        if (index == currentPage) {
            state.prepareContent(index)
            VideoCard(
                state = state,
                page = currentPage
            )
        }
    }
}

@Composable
fun VideoCard(
    state: VideoUiState,
    page: Int,
    viewModel: TikTokViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> viewModel.createPlayer(context)
            Lifecycle.Event.ON_STOP -> viewModel.releasePlayer()
            else -> {}
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.player != null) {
            Player(
                player = state.player!!
            )
        }
        if (!state.showPlayer) {
            val video = state.videos[page]
            Image(
                painter = rememberAsyncImagePainter(
                    model = video.previewImageUri
                ),
                contentDescription = "Preview",
                modifier = Modifier
                    .aspectRatio(video.aspectRatio ?: 1f)
                    .align(Alignment.Center)
                    .fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Player(
    player: Player,
    modifier: Modifier = Modifier
) {
    val playerView = rememberPlayerView(player)
    var playPauseIconVisibility by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    var job: Job? by remember {
        mutableStateOf(null)
    }
    Box {
        AndroidView(
            factory = { playerView },
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            playPauseIconVisibility = false
                            job?.cancel()
                            playerView.player?.playWhenReady =
                                playerView.player?.playWhenReady?.not() == true
                            job = coroutineScope.launch {
                                playPauseIconVisibility = true
                                delay(800)
                                playPauseIconVisibility = false
                            }
                        }
                    )
                }
        )
        AnimatedVisibility(
            visible = playPauseIconVisibility,
            enter = scaleIn(
                spring(Spring.DampingRatioMediumBouncy)
            ),
            exit = scaleOut(tween(150)),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(
                painter = painterResource(
                    id = if (playerView.player?.playWhenReady == true)
                    com.example.tiktokcompose.R.drawable.play
                else
                    com.example.tiktokcompose.R.drawable.pause
                ),
                contentDescription = null,
                tint = Color.White.copy(0.90f),
                modifier = Modifier
                    .size(100.dp)
            )
        }
    }
}

@Composable
fun rememberPlayerView(player: Player): PlayerView {
    val context = LocalContext.current
    val playerView = remember {
        PlayerView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
            this.player = player
        }
    }
    LaunchedEffect(key1 = player) {
        playerView.player = player
    }
    DisposableEffect(key1 = true) {
        onDispose {
            playerView.player = null
        }
    }
    return playerView
}