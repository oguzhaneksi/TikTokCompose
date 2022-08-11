package com.example.tiktokcompose.ui.state

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.example.tiktokcompose.domain.models.VideoData

data class VideoUiState(
    var player: Player? = null,
    val videos: List<VideoData> = emptyList()
) {

    val showPlayer: Boolean
        get() {
            return player?.playbackState == Player.STATE_READY
        }

    fun prepareContent(index: Int) {
        val mediaItem = MediaItem.fromUri(videos[index].mediaUri)
        player?.let { exoPlayer->
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.playWhenReady = true
            exoPlayer.prepare()
        }
    }
}