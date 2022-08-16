package com.example.tiktokcompose.util

import androidx.media3.common.MediaItem
import com.example.tiktokcompose.domain.models.VideoData

fun List<VideoData>.toMediaItems(): List<MediaItem> {
    return map {
        MediaItem.fromUri(it.mediaUri)
    }
}