package com.example.tiktokcompose.ui.state

import androidx.media3.common.Player
import com.example.tiktokcompose.domain.models.VideoData

data class VideoUiState(
    val player: Player? = null,
    val videos: List<VideoData> = emptyList()
) {

    fun playMediaAt(position: Int) {
        player?.let { player->
            if (player.currentMediaItemIndex == position && player.isPlaying)
                return

            player.seekToDefaultPosition(position)
            player.playWhenReady = true
            player.prepare()
        }
    }
}